package br.com.fiap.mscart.controller;

import br.com.fiap.mscart.entity.Cart;
import br.com.fiap.mscart.entity.request.ItemRequest;
import br.com.fiap.mscart.entity.response.CartResponse;
import br.com.fiap.mscart.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
	private final CartServiceImpl cartService;

	@GetMapping("/getOpenCart")
	public ResponseEntity<CartResponse> getCartByUsername(HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(this.cartService.getOpenCartByUsername(username));
	}

	@PostMapping("/addItem")
	public ResponseEntity<CartResponse> addItem(HttpServletRequest request, @RequestBody @Valid ItemRequest item) {
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(this.cartService.addItem(username, item));
	}

	@PostMapping("/removeItem")
	public ResponseEntity<CartResponse> removeItem(HttpServletRequest request, @RequestBody @Valid ItemRequest item) {
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(this.cartService.removeItem(username, item));
	}
}
