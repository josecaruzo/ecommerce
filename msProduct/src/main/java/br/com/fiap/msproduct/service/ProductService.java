package br.com.fiap.msproduct.service;

import br.com.fiap.msproduct.entity.Product;
import br.com.fiap.msproduct.entity.request.ProductRequest;
import br.com.fiap.msproduct.entity.response.ProductResponse;

import java.util.List;

public interface ProductService {
	public List<ProductResponse> getAllProducts();
	public ProductResponse getProductById(Long id);
	public ProductResponse createProduct(ProductRequest productRequest);
	public ProductResponse updateProduct(Long id, ProductRequest productRequest);
	public String deleteProduct(Long id);

	public Product findProduct(Long id);
	public void saveProduct(Product product);
}
