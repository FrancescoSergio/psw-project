package it.francesco.ecommerceserver.controllers;

import it.francesco.ecommerceserver.support.exceptions.PurchaseNotFoundException;
import it.francesco.ecommerceserver.model.Purchase;
import it.francesco.ecommerceserver.model.User;
import it.francesco.ecommerceserver.services.PurchaseService;
import it.francesco.ecommerceserver.support.exceptions.QuantityProductUnavailableException;
import it.francesco.ecommerceserver.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/{user}")
    public ResponseEntity<List<Purchase>> getPurchasesForUser(@RequestBody @Validated User user) throws UserNotFoundException {
        List<Purchase> userPurchases = purchaseService.findPurchasesByBuyer(user);
        return new ResponseEntity<>(userPurchases, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll() {
        List<Purchase> allPurchases = purchaseService.findAllPurchases();
        return new ResponseEntity<>(allPurchases,HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Purchase> getPurchaseById(@RequestParam Long id) throws PurchaseNotFoundException {
        Purchase purchase = purchaseService.findPurchaseById(id);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Purchase> updatePurchase(@RequestBody @Validated Purchase purchase) throws PurchaseNotFoundException {
        Purchase updatedPurchase = purchaseService.updatePurchase(purchase);
        return new ResponseEntity<>(updatedPurchase,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePurchase(@RequestParam Long id) throws PurchaseNotFoundException {
        purchaseService.deletePurchase(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}