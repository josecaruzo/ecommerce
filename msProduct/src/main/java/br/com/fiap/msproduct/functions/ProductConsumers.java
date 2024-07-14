package br.com.fiap.msproduct.functions;

import br.com.fiap.msproduct.entity.Product;
import br.com.fiap.msproduct.service.impl.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ProductConsumers {
	private final ProductServiceImpl productService;

	public ProductConsumers(ProductServiceImpl productService) {
		this.productService = productService;
	}

	@Bean
	Consumer<Product> saveProduct(){
		return productService::saveProduct;
	}
}
