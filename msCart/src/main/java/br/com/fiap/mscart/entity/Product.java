package br.com.fiap.mscart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
	private Long id;
	private String name;
	private String description;
	private Float price;
	private Integer quantity;
	private String url;
}
