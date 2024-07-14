package br.com.fiap.mspayment.entity;

import br.com.fiap.mspayment.entity.enums.PaymentMethodType;
import br.com.fiap.mspayment.entity.enums.StatusType;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String username;

	@Column(length = 6, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusType status;

	@Column
	private Integer totalItems;

	@Column
	private Float totalPrice;

	@Column(length = 25)
	@Enumerated(EnumType.STRING)
	private PaymentMethodType paymentMethod;

	@Column
	private LocalDateTime paymentDate;

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<Item> items;
}