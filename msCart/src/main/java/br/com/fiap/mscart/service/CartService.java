package br.com.fiap.mscart.service;

import br.com.fiap.mscart.entity.request.ItemRequest;
import br.com.fiap.mscart.entity.response.CartResponse;

public interface CartService {
	public CartResponse getOpenCartByUsername(String username);
	public CartResponse addItem(String username, ItemRequest item);
	public CartResponse removeItem(String username, ItemRequest item);
}
