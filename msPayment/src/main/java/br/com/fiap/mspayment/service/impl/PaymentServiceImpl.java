package br.com.fiap.mspayment.service.impl;

import br.com.fiap.mspayment.entity.Cart;
import br.com.fiap.mspayment.entity.Item;
import br.com.fiap.mspayment.entity.enums.StatusType;
import br.com.fiap.mspayment.entity.request.PaymentRequest;
import br.com.fiap.mspayment.entity.response.PayedCartResponse;
import br.com.fiap.mspayment.entity.response.PaymentHistoryResponse;
import br.com.fiap.mspayment.entity.Product;
import br.com.fiap.mspayment.entity.response.ReviewedCartResponse;
import br.com.fiap.mspayment.functions.ProductFunctions;
import br.com.fiap.mspayment.repository.CartRepository;
import br.com.fiap.mspayment.service.PaymentService;
import br.com.fiap.mspayment.service.mapper.CartMapper;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	private static final String CART_NOT_FOUND = "O usuário logado não possui carrinho de compras aberto"; //User does not have an open shopping cart
	private static final String PRODUCT_NOT_FOUND = "Produto %s não encontrado";
	private static final String PRODUCT_DOESNT_HAVE_ENOUGH_STOCK = "Estoque insuficiente para o produto %s";

	private final CartRepository cartRepository;
	private final ProductFunctions productFunctions;

	public List<PaymentHistoryResponse> getPaymentHistoryByUsername(String username) {
		List<Cart> cartList = this.cartRepository.findByUsernameAndStatus(username, StatusType.PAID);
		return cartList.stream().map(CartMapper::cartEntityToPaymentHistoryResponse).toList();
	}

	public ReviewedCartResponse reviewCartByUsername(String username) {
		Cart cart = getOpenCartByUsername(username);
		cart.setTotalPrice(0f); //Reset total price - to avoid inconsistencies
		AtomicReference<Float> totalPrice = new AtomicReference<>(0f);

		cart.getItems().forEach(item -> {
			Product product = findProduct(item);
			setItemValues(item, product);
			totalPrice.updateAndGet(v -> v + item.getItemTotalPrice());
		});

		cart.setTotalPrice(totalPrice.get());
		return CartMapper.cartEntityToReviewedCartResponse(this.cartRepository.save(cart));
	}

	public PayedCartResponse payCartByUsername(String username, PaymentRequest paymentMethod) {

		Cart cart = getOpenCartByUsername(username);
		cart.setTotalPrice(0f); //Reset total price - to avoid inconsistencies
		AtomicReference<Float> totalPrice = new AtomicReference<>(0f);

		cart.getItems().forEach(item -> {
			Product product = findProduct(item);
			setItemValues(item, product);
			totalPrice.updateAndGet(v -> v + item.getItemTotalPrice());

			product.setQuantity(product.getQuantity() - item.getQuantity());
			productFunctions.saveProduct(product);
		});

		cart.setTotalPrice(totalPrice.get());
		cart.setStatus(StatusType.PAID); //Set cart status to PAYED
		cart.setPaymentMethod(paymentMethod.getPaymentMethod());
		cart.setPaymentDate(LocalDateTime.now());
		return CartMapper.cartEntityToPayedCartResponse(this.cartRepository.save(cart));
	}

	private Cart getOpenCartByUsername(String username) {
		List<Cart> cartList = this.cartRepository.findByUsernameAndStatus(username, StatusType.OPEN);

		if (cartList.isEmpty()) {
			throw new EntityNotFoundException(CART_NOT_FOUND);
		}

		return cartList.get(0);
	}

	private Product findProduct(Item item){
		try {
			Product product = productFunctions.findProduct(item.getProductId());
			//Before trying to add the item to the cart, check if the product has enough stock
			if (product.getQuantity() < item.getQuantity()) {
				throw new DataIntegrityViolationException(String.format(PRODUCT_DOESNT_HAVE_ENOUGH_STOCK, item.getProductId()));
			}
			return product;
		}catch (FeignException.NotFound e) {
			throw new EntityNotFoundException(String.format(PRODUCT_NOT_FOUND, item.getProductId()));
		}
	}

	private void setItemValues(Item item, Product product){
		item.setName(product.getName());
		item.setUnitPrice(product.getPrice());
		item.setItemTotalPrice(item.getQuantity() * item.getUnitPrice());
	}
}
