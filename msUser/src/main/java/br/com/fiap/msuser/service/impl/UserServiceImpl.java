package br.com.fiap.msuser.service.impl;

import br.com.fiap.msuser.entity.User;
import br.com.fiap.msuser.entity.request.LoginRequest;
import br.com.fiap.msuser.entity.request.UserRequest;
import br.com.fiap.msuser.entity.response.TokenResponse;
import br.com.fiap.msuser.entity.response.UserResponse;
import br.com.fiap.msuser.repository.UserRepository;
import br.com.fiap.msuser.security.authentication.JwtTokenService;
import br.com.fiap.msuser.security.userdetails.UserDetailsImpl;
import br.com.fiap.msuser.service.UserService;
import br.com.fiap.msuser.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	public static final String USER_ALREADY_EXISTS = "Usuário %s já cadastrado"; // User already registered

	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;

	public List<UserResponse> getAll() {
		return this.userRepository.findAll().stream().map(UserMapper::userEntityToUserResponse).toList();
	}

	public UserResponse register(UserRequest userRequest){
		newUserValidation(userRequest);

		String encryptedPassword = new BCryptPasswordEncoder().encode(userRequest.getPassword());
		userRequest.setPassword(encryptedPassword);

		User user = UserMapper.userRequestToUserEntity(userRequest);
		return UserMapper.userEntityToUserResponse(this.userRepository.save(user));
	}

	public TokenResponse login(LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return TokenResponse.builder()
				.token(jwtTokenService.generateToken(userDetails))
				.build();
	}

	private void newUserValidation(UserRequest userRequest){
		if(this.userRepository.findByUsername(userRequest.getUsername()).isPresent()){
			throw new DataIntegrityViolationException(String.format(USER_ALREADY_EXISTS, userRequest.getUsername())); // User Id already exists
		}

		if(this.userRepository.findByUsername(userRequest.getUsername()).isPresent()){
			throw new DataIntegrityViolationException(String.format(USER_ALREADY_EXISTS, userRequest.getUsername())); // Username already exists
		}
	}
}