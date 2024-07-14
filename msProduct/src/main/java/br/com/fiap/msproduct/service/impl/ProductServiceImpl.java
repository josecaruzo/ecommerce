package br.com.fiap.msproduct.service.impl;

import br.com.fiap.msproduct.entity.Product;
import br.com.fiap.msproduct.entity.request.ProductRequest;
import br.com.fiap.msproduct.entity.response.ProductResponse;
import br.com.fiap.msproduct.repository.ProductRepository;
import br.com.fiap.msproduct.service.ProductService;
import br.com.fiap.msproduct.service.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	public static final String ENTITY_NOT_FOUND = "Produto não encontrado"; //Product not found
	public static final String PRODUCT_ALREADY_EXISTS = "Produto já cadastrado com esse nome"; //Product already registered
	public static final String PRODUCT_HAS_STOCK = "O produto tem quantidade em estoque, ele não pode ser deletado."; //The product has stock, it can't be deleted
	public static final String PRODUCT_DELETED = "Produto %s deletado com sucesso"; //Product %s deleted successfully

	private final ProductRepository productRepository;

	public List<ProductResponse> getAllProducts() {
		return this.productRepository.findAll().stream().map(ProductMapper::productEntityToProductResponse).toList();
	}

	public ProductResponse getProductById(Long id) {
		Product product = this.productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
		return ProductMapper.productEntityToProductResponse(product);
	}

	public ProductResponse createProduct(ProductRequest productRequest) {
		//Before creating, check if the name already exists
		Product product = ProductMapper.productRequestToProductEntity(productRequest);
		validateIfNameExists(0L, product.getName());
		return ProductMapper.productEntityToProductResponse(this.productRepository.save(product));
	}

	public ProductResponse updateProduct(Long id, ProductRequest productRequest){
		Product product = ProductMapper.productRequestToProductEntity(productRequest);
		Product productToUpdate = this.productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

		//Before updating, check if the name already exists
		validateIfNameExists(id, product.getName());

		productToUpdate.setName(product.getName());
		productToUpdate.setDescription(product.getDescription());
		productToUpdate.setPrice(product.getPrice());
		productToUpdate.setQuantity(product.getQuantity());
		productToUpdate.setUrl(product.getUrl());

		return ProductMapper.productEntityToProductResponse(this.productRepository.save(productToUpdate));
	}

	public String deleteProduct(Long id){
		Product productToDelete = this.productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

		//If the product has stock, it can't be deleted
		if(productToDelete.getQuantity() > 0){
			throw new DataIntegrityViolationException(PRODUCT_HAS_STOCK);
		}

		this.productRepository.delete(productToDelete);
		return String.format(PRODUCT_DELETED, productToDelete.getName());
	}

	public Product findProduct(Long id){
		return this.productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
	}

	public void saveProduct(Product product){
		this.productRepository.save(product);
	}

	private void validateIfNameExists(Long id, String name) {
		Product findProduct = this.productRepository.findByNameEqualsIgnoreCase(name).orElse(null);

		//If found a product with the same name and different id
		if (findProduct != null && findProduct.getId().longValue() != id) {
			throw new DataIntegrityViolationException(PRODUCT_ALREADY_EXISTS);
		}
	}


}
