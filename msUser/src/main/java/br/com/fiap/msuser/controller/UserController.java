package br.com.fiap.msuser.controller;

import br.com.fiap.msuser.entity.User;
import br.com.fiap.msuser.entity.request.LoginRequest;
import br.com.fiap.msuser.entity.request.UserRequest;
import br.com.fiap.msuser.entity.response.TokenResponse;
import br.com.fiap.msuser.entity.response.UserResponse;
import br.com.fiap.msuser.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping("/getAll")
	public ResponseEntity<List<UserResponse>> getAll(){
		return ResponseEntity.ok(this.userService.getAll());
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest user){
		return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.register(user));
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest){
		return ResponseEntity.ok(this.userService.login(loginRequest));
	}

}
