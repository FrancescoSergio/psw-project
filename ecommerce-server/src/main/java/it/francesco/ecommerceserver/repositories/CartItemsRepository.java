package it.francesco.ecommerceserver.repositories;

import it.francesco.ecommerceserver.model.Cart;
import it.francesco.ecommerceserver.model.CartItem;
import it.francesco.ecommerceserver.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    CartItem findCartItemById(Long id);

    CartItem findByProduct_IdAndCart_Id(Long productId, Long cartId);

    boolean existsByCart(Cart cart);

    boolean existsByProduct_IdAndCart_Id(Long productId, Long cartId);

    List<CartItem> findByCart_Id(Long id);

    void deleteAllByCart_Id(Long cartId);
}
