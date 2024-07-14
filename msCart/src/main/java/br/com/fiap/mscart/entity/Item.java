package br.com.fiap.mscart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long productId;

	@Column(length = 50, nullable = false)
	private String name;

	@Column
	private Float unitPrice;

	@Column(nullable = false)
	private Integer quantity;

	@Column
	private Float itemTotalPrice;
}
