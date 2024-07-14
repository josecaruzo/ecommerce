package br.com.fiap.msuser.service;

import br.com.fiap.msuser.entity.request.LoginRequest;
import br.com.fiap.msuser.entity.request.UserRequest;
import br.com.fiap.msuser.entity.response.TokenResponse;
import br.com.fiap.msuser.entity.response.UserResponse;

import java.util.List;

public interface UserService {
	List<UserResponse> getAll();
	UserResponse register(UserRequest userRequest);
	TokenResponse login(LoginRequest loginRequest);
}
