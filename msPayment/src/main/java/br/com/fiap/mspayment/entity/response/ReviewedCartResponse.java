package br.com.fiap.mspayment.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewedCartResponse {
	private Long cartId;
	private String status;
	private Integer totalItems;
	private List<ItemResponse> items;
	private Float totalPrice;
}
