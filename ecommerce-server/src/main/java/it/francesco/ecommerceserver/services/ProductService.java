package it.francesco.ecommerceserver.services;

import it.francesco.ecommerceserver.model.Product;
import it.francesco.ecommerceserver.model.ProductType;
import it.francesco.ecommerceserver.repositories.ProductsRepository;
import it.francesco.ecommerceserver.support.exceptions.ProductAlreadyInRepoException;
import it.francesco.ecommerceserver.support.exceptions.ProductNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Transactional(readOnly = false)
    public Product addProduct(Product product) throws ProductAlreadyInRepoException {
        if(productsRepository.existsByName(product.getName())){
            throw new ProductAlreadyInRepoException();
        }
        return productsRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProducts(){
        return productsRepository.findAll();
    }


    //Versione per risultati paginati (quando il numero di oggetti è troppo grande)
    @Transactional(readOnly = true)
    public List<Product> showAllProducts(int pageNumber, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productsRepository.findAll(paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long id) throws ProductNotFoundException {
        if(productsRepository.existsById(id)){
            throw new ProductNotFoundException();
        }
        return productsRepository.findProductById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findProductByName(String name) {
        if(name == ""){
            return findAllProducts();
        }
        List<Product> allProducts = findAllProducts();
        List<Product> result = new ArrayList<>();
        for(Product p : allProducts){
            if(name.toLowerCase().indexOf(p.getName().toLowerCase())!=-1) {
                result.add(p);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Product> findProductsByType(ProductType type){
        return productsRepository.findByType(type);
    }

    @Transactional(readOnly = false)
    public Product updateProduct(Product product) throws OptimisticLockException, ProductNotFoundException {
        if(!productsRepository.existsByName(product.getName())){
            throw new ProductNotFoundException();
        }
        return productsRepository.save(product);
    }

    @Transactional(readOnly = false)
    public void deleteProduct(Long id) throws ProductNotFoundException {
        if(!productsRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productsRepository.deleteProductById(id);
    }

}
