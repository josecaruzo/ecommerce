package br.com.fiap.mscart.service.impl;

import br.com.fiap.mscart.entity.Cart;
import br.com.fiap.mscart.entity.Item;
import br.com.fiap.mscart.entity.Product;
import br.com.fiap.mscart.entity.enums.StatusType;
import br.com.fiap.mscart.entity.request.ItemRequest;
import br.com.fiap.mscart.entity.response.CartResponse;
import br.com.fiap.mscart.functions.ProductFunctions;
import br.com.fiap.mscart.repository.CartRepository;
import br.com.fiap.mscart.service.CartService;
import br.com.fiap.mscart.service.mapper.CartMapper;
import br.com.fiap.mscart.service.mapper.ItemMapper;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private static final String CART_NOT_FOUND = "O usuário logado não possui carrinho de compras aberto"; //User does not have an open shopping cart
	private static final String PRODUCT_NOT_FOUND = "Produto %s não encontrado";
	private static final String PRODUCT_DOESNT_HAVE_ENOUGH_STOCK = "Estoque insuficiente para o produto %s"; //Insufficient stock
	private static final String ITEM_NOT_FOUND = "Item %s não encontrado no carrinho";
	private static final String ITEM_DOESNT_HAVE_ENOUGH_QUANTITY = "Carrinho de compras não tem quantidade suficiente do produto %s  "; //Shopping cart does not have enough quantity of item %s

	private final CartRepository cartRepository;
	private final ProductFunctions productFunctions;

	public CartResponse getOpenCartByUsername(String username) {
		List<Cart> cartList = this.cartRepository.findByUsernameAndStatus(username, StatusType.OPEN);

		if (cartList.isEmpty()) {
			throw new EntityNotFoundException(String.format(CART_NOT_FOUND));
		}

		return CartMapper.cartEntityToCartResponse(cartList.get(0));
	}

	public CartResponse addItem(String username, ItemRequest itemRequest) {
		Item item = ItemMapper.itemRequestToItemEntity(itemRequest);
		try{
			Product product = productFunctions.findProduct(item.getProductId());

			//Before trying to add the item to the cart, check if the product has enough stock
			if (product.getQuantity() < item.getQuantity()) {
				throw new DataIntegrityViolationException(String.format(PRODUCT_DOESNT_HAVE_ENOUGH_STOCK, item.getProductId()));
			}

			item.setName(product.getName());

			List<Cart> cartList = this.cartRepository.findByUsernameAndStatus(username, StatusType.OPEN);
			Cart cart;
			if (cartList.isEmpty()) { //Create a new Cart
				cart = new Cart();
				cart.setUsername(username);
				cart.setStatus(StatusType.OPEN);
				cart.setTotalItems(1);
				cart.setTotalPrice(0f);
				cart.setItems(List.of(item));
			} else { //Update existing Cart
				cart = cartList.get(0);
				List<Item> items = cart.getItems();
				//Validate if the item exists in the cart
				items.stream().filter(i -> i.getProductId().equals(item.getProductId())).findFirst()
						.ifPresentOrElse(
								i -> i.setQuantity(i.getQuantity() + item.getQuantity()), //If item exists in cart, update quantity
								() -> { //If item doesn't exist in cart, add it to cart
									items.add(item);
									cart.setTotalItems(cart.getTotalItems() + 1);
								}
						);

				cart.setItems(items);
			}
			return CartMapper.cartEntityToCartResponse(this.cartRepository.save(cart));
		}catch (FeignException.NotFound e) {
			throw new EntityNotFoundException(String.format(PRODUCT_NOT_FOUND, item.getProductId()));
		}
	}

	public CartResponse removeItem(String username, ItemRequest itemRequest) {
		Item item = ItemMapper.itemRequestToItemEntity(itemRequest);
		List<Cart> cartList = this.cartRepository.findByUsernameAndStatus(username, StatusType.OPEN);

		if (cartList.isEmpty()) {
			throw new EntityNotFoundException(String.format(CART_NOT_FOUND));
		}

		Cart cart = cartList.get(0);
		List<Item> items = cart.getItems();

		//Validate if the item exists in the cart
		items.stream().filter(i -> i.getProductId().equals(item.getProductId())).findFirst()
				.ifPresentOrElse(
						i -> {
							if(i.getQuantity() < item.getQuantity()){ //Quantity in cart less than requested quantity
								throw new DataIntegrityViolationException(String.format(ITEM_DOESNT_HAVE_ENOUGH_QUANTITY, item.getProductId()));
							}
							else if(i.getQuantity() > item.getQuantity() ){ //Quantity is cart more than requested quantity
								i.setQuantity(i.getQuantity() - item.getQuantity());
							}
							else{ //Same than cart quantity remove item from cart
								items.remove(i);
								cart.setTotalItems(cart.getTotalItems() - 1);
							}
						},
						() -> {throw new EntityNotFoundException(String.format(ITEM_NOT_FOUND, item.getProductId()));}
				);

		//If cart is empty, delete it
		if(cart.getTotalItems() == 0){
			cartRepository.delete(cart);
			return null;
		}

		cart.setItems(items); //update items
		return CartMapper.cartEntityToCartResponse(this.cartRepository.save(cart));
	}
}

