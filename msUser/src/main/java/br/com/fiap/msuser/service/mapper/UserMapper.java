package br.com.fiap.msuser.service.mapper;

import br.com.fiap.msuser.entity.Role;
import br.com.fiap.msuser.entity.User;
import br.com.fiap.msuser.entity.enums.RoleType;
import br.com.fiap.msuser.entity.request.UserRequest;
import br.com.fiap.msuser.entity.response.UserResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserMapper {

	public User userRequestToUserEntity(UserRequest userRequest){
		return User.builder()
				.username(userRequest.getUsername())
				.password(userRequest.getPassword())
				.roles(List.of(Role.builder()
						.id( userRequest.getRole().equals(RoleType.ROLE_ADMIN) ? 1L : 2L)
						.roleName(userRequest.getRole()).build()))
				.build();
	}

	public UserResponse userEntityToUserResponse (User user){
		return UserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.role(user.getRoles().get(0).getRoleName())
				.build();
	}
}
