package br.com.fiap.mspayment.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayedCartResponse {
	private Long cartId;
	private String status;
	private Integer totalItems;
	private List<ItemResponse> items;
	private Float totalPrice;
	private String paymentMethod;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime paymentDate;
}