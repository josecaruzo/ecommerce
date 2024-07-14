package br.com.fiap.mscart.repository;

import br.com.fiap.mscart.entity.Cart;
import br.com.fiap.mscart.entity.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	List<Cart> findByUsernameAndStatus(String userId, StatusType status);
}
