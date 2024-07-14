package br.com.fiap.mspayment.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
	private Long productId;
	private String name;
	private Float unitPrice;
	private Integer quantity;
	private Float itemTotalPrice;
}
