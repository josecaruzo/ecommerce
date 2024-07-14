package br.com.fiap.mscart.service.mapper;

import br.com.fiap.mscart.entity.Cart;
import br.com.fiap.mscart.entity.response.CartResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartMapper {
	public CartResponse cartEntityToCartResponse(Cart cart){
		return CartResponse.builder()
				.id(cart.getId())
				.status(cart.getStatus().getValue())
				.totalItems(cart.getTotalItems())
				.items(cart.getItems().stream().map(ItemMapper::itemEntityToItemResponse).toList())
				.build();
	}
}
