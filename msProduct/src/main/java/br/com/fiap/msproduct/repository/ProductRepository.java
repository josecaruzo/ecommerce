package br.com.fiap.msproduct.repository;

import br.com.fiap.msproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByNameEqualsIgnoreCase(String name);
}
