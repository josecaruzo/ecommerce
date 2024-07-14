package br.com.fiap.msproduct.controller;

import br.com.fiap.msproduct.entity.Product;
import br.com.fiap.msproduct.entity.request.ProductRequest;
import br.com.fiap.msproduct.entity.response.ProductResponse;
import br.com.fiap.msproduct.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
	private final ProductServiceImpl productService;

	@GetMapping("/getProductById/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		return ResponseEntity.ok(this.productService.getProductById(id));
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		return ResponseEntity.ok(this.productService.getAllProducts());
	}

	@PostMapping("/createProduct")
	public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest product) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.createProduct(product));
	}

	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest product) {
		return ResponseEntity.ok(this.productService.updateProduct(id, product));
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		return ResponseEntity.ok(this.productService.deleteProduct(id));
	}
}
