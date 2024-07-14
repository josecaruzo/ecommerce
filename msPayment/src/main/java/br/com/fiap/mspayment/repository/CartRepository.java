package br.com.fiap.mspayment.repository;

import br.com.fiap.mspayment.entity.Cart;
import br.com.fiap.mspayment.entity.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByUsernameAndStatus(String username, StatusType status);
}
