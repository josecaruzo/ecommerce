package br.com.fiap.mspayment.controller;

import br.com.fiap.mspayment.entity.request.PaymentRequest;
import br.com.fiap.mspayment.entity.response.PayedCartResponse;
import br.com.fiap.mspayment.entity.response.PaymentHistoryResponse;
import br.com.fiap.mspayment.entity.response.ReviewedCartResponse;
import br.com.fiap.mspayment.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
	private final PaymentServiceImpl paymentService;

	@GetMapping("/getPaymentHistory")
	public ResponseEntity<List<PaymentHistoryResponse>> getPaymentHistoryByUsername(HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(paymentService.getPaymentHistoryByUsername(username));
	}

	@PostMapping("/reviewCart")
	public ResponseEntity<ReviewedCartResponse> reviewCartByUsername(HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(paymentService.reviewCartByUsername(username));
	}

	@PostMapping("/payCart")
	public ResponseEntity<PayedCartResponse> payCartByUsername(HttpServletRequest request, @RequestBody @Valid PaymentRequest paymentRequest) {
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(paymentService.payCartByUsername(username, paymentRequest));
	}

}
