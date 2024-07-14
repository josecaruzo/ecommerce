package br.com.fiap.msproduct.repository;

import br.com.fiap.msproduct.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

	Optional<User> findByUsername(String username);
}
