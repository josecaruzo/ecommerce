package br.com.fiap.msuser.entity.request;

import br.com.fiap.msuser.entity.enums.RoleType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest{

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@Valid
	@NotNull
	private RoleType role;
}
