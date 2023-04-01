package it.francesco.ecommerceserver.services;

import it.francesco.ecommerceserver.model.Product;
import it.francesco.ecommerceserver.model.ProductInPurchase;
import it.francesco.ecommerceserver.model.Purchase;
import it.francesco.ecommerceserver.model.User;
import it.francesco.ecommerceserver.repositories.ProductInPurchaseRepository;
import it.francesco.ecommerceserver.repositories.ProductsRepository;
import it.francesco.ecommerceserver.repositories.PurchaseRepository;
import it.francesco.ecommerceserver.repositories.UserRepository;
import it.francesco.ecommerceserver.support.exceptions.PurchaseNotFoundException;
import it.francesco.ecommerceserver.support.exceptions.QuantityProductUnavailableException;
import it.francesco.ecommerceserver.support.exceptions.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ProductsRepository productsRepository, ProductInPurchaseRepository productInPurchaseRepository, UserRepository userRepository, EntityManager entityManager) {
        this.purchaseRepository = purchaseRepository;
        this.productsRepository = productsRepository;
        this.productInPurchaseRepository = productInPurchaseRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    private final ProductsRepository productsRepository;

    private final ProductInPurchaseRepository productInPurchaseRepository;


    private final UserRepository userRepository;


    private final EntityManager entityManager;

    @Transactional(readOnly = false)
    public void addPurchase(Purchase purchase) throws QuantityProductUnavailableException, OptimisticLockException {
        List<Product> result = new ArrayList<>();
        for ( ProductInPurchase pip : purchase.getProductsInPurchase() ) {
            //Product product = pip.getProduct();
            ProductInPurchase justAdded = productInPurchaseRepository.save(pip);
            Product product = justAdded.getProduct();
            int newQuantity = product.getQuantity() - pip.getQuantity();
            if ( newQuantity < 0 ) {
                throw new QuantityProductUnavailableException();
            }
            product.setQuantity(newQuantity);
            productsRepository.save(product);
        }
    }

    @Transactional(readOnly = true)
    public List<Purchase> findPurchasesByBuyer(User user) throws UserNotFoundException {
        if ( !userRepository.existsById(user.getId()) ) {
            throw new UserNotFoundException();
        }
        return purchaseRepository.findPurchaseByBuyer(user);
    }

    @Transactional(readOnly = true)
    public List<Purchase> findAllPurchases(){
        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Purchase findPurchaseById(Long id) throws PurchaseNotFoundException {
        if(purchaseRepository.existsById(id)){
            return purchaseRepository.findPurchaseById(id);
        }else{
            throw new PurchaseNotFoundException();
        }
    }

    @Transactional(readOnly = false)
    public Purchase updatePurchase(Purchase purchase) throws PurchaseNotFoundException {
        if(purchaseRepository.existsByBuyer(purchase.getBuyer())){
            return purchaseRepository.save(purchase);
        }
        else{
            throw new PurchaseNotFoundException();
        }
    }

    @Transactional(readOnly = false)
    public void deletePurchase(Long id) throws PurchaseNotFoundException {
        if(purchaseRepository.existsById(id)) {
            purchaseRepository.deletePurchaseById(id);
        }
        else{
            throw new PurchaseNotFoundException();
        }
    }
}
