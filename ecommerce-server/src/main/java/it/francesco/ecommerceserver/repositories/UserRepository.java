package it.francesco.ecommerceserver.repositories;

import it.francesco.ecommerceserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findUserById(Long id);

    User findUserByEmailAndPassword(String email, String password);

    void deleteUserById(Long id);

    boolean existsByEmail(String email);
}