package it.francesco.ecommerceserver.repositories;

import it.francesco.ecommerceserver.model.Product;
import it.francesco.ecommerceserver.model.ProductInPurchase;
import it.francesco.ecommerceserver.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Long> {

    List<ProductInPurchase> findProductInPurchaseByProductAndPurchase(Product product, Purchase purchase);

    ProductInPurchase findProductInPurchaseById(Long id);

    boolean existsById(Long id);
}
