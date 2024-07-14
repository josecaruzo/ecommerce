package br.com.fiap.msproduct.functions;

import br.com.fiap.msproduct.entity.Product;
import br.com.fiap.msproduct.service.impl.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductFunctions {
	private final ProductServiceImpl productService;

	public ProductFunctions(ProductServiceImpl productService) {
		this.productService = productService;
	}

	@Bean
	Function<Long, Product> findProduct() {
		return this.productService::findProduct;
	}
}
