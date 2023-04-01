package it.francesco.ecommerceserver.repositories;

import it.francesco.ecommerceserver.model.Product;
import it.francesco.ecommerceserver.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product,Long> {

    List<Product> findByName(String name);

    List<Product> findByType(ProductType type);

    Product findProductById(Long id);

    void deleteProductById(Long id);

    boolean existsByName(String name);
}
