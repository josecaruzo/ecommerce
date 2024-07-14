package br.com.fiap.mscart.functions;

import br.com.fiap.mscart.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "product", url = "http://msproduct:8082/api")
public interface ProductFunctions {

	@PostMapping("/saveProduct")
	void saveProduct(Product product);

	@GetMapping("/findProduct/{id}")
	Product findProduct(@PathVariable Long id);
}
