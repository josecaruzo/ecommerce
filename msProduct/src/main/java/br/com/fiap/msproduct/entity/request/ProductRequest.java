package br.com.fiap.msproduct.entity.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@Positive
	@NotNull
	private Float price;

	@PositiveOrZero
	@NotNull
	private Integer quantity;

	@URL
	@NotBlank
	private String url;
}
