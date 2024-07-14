package br.com.fiap.msproduct.service.mapper;

import br.com.fiap.msproduct.entity.Product;
import br.com.fiap.msproduct.entity.request.ProductRequest;
import br.com.fiap.msproduct.entity.response.ProductResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

	public Product productRequestToProductEntity(ProductRequest productRequest){
		return Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.quantity(productRequest.getQuantity())
				.url(productRequest.getUrl())
				.build();
	}

	public ProductResponse productEntityToProductResponse(Product product){
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.quantity(product.getQuantity())
				.url(product.getUrl())
				.build();
	}
}
