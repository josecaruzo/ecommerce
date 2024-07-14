package br.com.fiap.mspayment.entity.request;

import br.com.fiap.mspayment.entity.enums.PaymentMethodType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
	@Valid
	@NotNull
	private PaymentMethodType paymentMethod;
}
