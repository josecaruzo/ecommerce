package br.com.fiap.mspayment.service;

import br.com.fiap.mspayment.entity.request.PaymentRequest;
import br.com.fiap.mspayment.entity.response.PayedCartResponse;
import br.com.fiap.mspayment.entity.response.PaymentHistoryResponse;
import br.com.fiap.mspayment.entity.response.ReviewedCartResponse;

import java.util.List;

public interface PaymentService {
	public List<PaymentHistoryResponse> getPaymentHistoryByUsername(String username);
	public ReviewedCartResponse reviewCartByUsername(String username);
	public PayedCartResponse payCartByUsername(String username, PaymentRequest paymentRequest);
}
