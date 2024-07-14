package br.com.fiap.mscart.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
	private Long id;
	private String status;
	private Integer totalItems;
	private List<ItemResponse> items;
}
