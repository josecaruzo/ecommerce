package br.com.fiap.mspayment.service.mapper;

import br.com.fiap.mspayment.entity.Cart;
import br.com.fiap.mspayment.entity.response.PayedCartResponse;
import br.com.fiap.mspayment.entity.response.PaymentHistoryResponse;
import br.com.fiap.mspayment.entity.response.ReviewedCartResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartMapper {

	public PayedCartResponse cartEntityToPayedCartResponse(Cart cart){
		return PayedCartResponse.builder()
				.cartId(cart.getId())
				.status(cart.getStatus().getValue())
				.totalItems(cart.getTotalItems())
				.totalPrice(cart.getTotalPrice())
				.paymentMethod(cart.getPaymentMethod().getValue())
				.paymentDate(cart.getPaymentDate())
				.items(cart.getItems().stream().map(ItemMapper::itemEntityToItemResponse).toList())
				.build();
	}

	public PaymentHistoryResponse cartEntityToPaymentHistoryResponse(Cart cart){
		return PaymentHistoryResponse.builder()
				.cartId(cart.getId())
				.totalItems(cart.getTotalItems())
				.totalPrice(cart.getTotalPrice())
				.paymentMethod(cart.getPaymentMethod().getValue())
				.paymentDate(cart.getPaymentDate())
				.build();
	}

	public ReviewedCartResponse cartEntityToReviewedCartResponse(Cart cart){
		return ReviewedCartResponse.builder()
				.cartId(cart.getId())
				.status(cart.getStatus().getValue())
				.totalItems(cart.getTotalItems())
				.totalPrice(cart.getTotalPrice())
				.items(cart.getItems().stream().map(ItemMapper::itemEntityToItemResponse).toList())
				.build();
	}

}
