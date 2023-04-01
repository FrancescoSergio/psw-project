package it.francesco.ecommerceserver.controllers;

import it.francesco.ecommerceserver.model.Product;
import it.francesco.ecommerceserver.model.ProductType;
import it.francesco.ecommerceserver.services.ProductService;
import it.francesco.ecommerceserver.support.exceptions.ProductAlreadyInRepoException;
import it.francesco.ecommerceserver.support.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<Product> getProductById(@RequestParam Long id) throws ProductNotFoundException {
        Product product = productService.findProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name){
        List<Product> products = productService.findProductByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/find/by_type")
    public ResponseEntity<List<Product>> getProductsByType(@RequestParam ProductType type){
        List<Product> products = productService.findProductsByType(type);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws ProductAlreadyInRepoException {
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws ProductNotFoundException {
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody Long id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
