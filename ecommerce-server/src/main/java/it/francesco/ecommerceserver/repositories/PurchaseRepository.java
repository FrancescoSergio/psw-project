package it.francesco.ecommerceserver.repositories;

import it.francesco.ecommerceserver.model.Purchase;
import it.francesco.ecommerceserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findPurchaseByBuyer(User buyer);

    Purchase findPurchaseById(Long ig);

    void deletePurchaseById(Long id);

    boolean existsById(Long id);

    boolean existsByBuyer(User buyer);
}
