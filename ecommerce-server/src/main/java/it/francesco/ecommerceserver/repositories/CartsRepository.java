package it.francesco.ecommerceserver.repositories;

import it.francesco.ecommerceserver.model.Cart;
import it.francesco.ecommerceserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends JpaRepository<Cart, Long> {

    Cart findCartByUser(User user);

    Cart findCartById(Long id);

    void deleteCartById(Long id);
    Boolean existsByUser(User user);

}
