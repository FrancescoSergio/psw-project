package it.francesco.ecommerceserver.controllers;

import it.francesco.ecommerceserver.support.exceptions.*;
import it.francesco.ecommerceserver.model.Cart;
import it.francesco.ecommerceserver.model.CartItem;
import it.francesco.ecommerceserver.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    
    private final CartService cartService;
    
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAll() {
        List<Cart> carts = cartService.findAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Cart> getCartByUser(@RequestParam String email) throws CartNotFoundException, UserNotFoundException {
        Cart cart = cartService.findCartByUser(email);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/itemslist")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam Long id) throws CartNotFoundException {
        List<CartItem> result = cartService.findItemsByCartId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<Cart> getCartById(@RequestParam Long id) throws CartNotFoundException {
        Cart cart = cartService.findCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@RequestBody @Validated Cart cart) throws  CartAlreadyInRepoException {
        Cart newCart = cartService.addCart(cart);
        return new ResponseEntity<>(newCart,HttpStatus.CREATED);
    }

    @PostMapping("/purchased")
    public ResponseEntity<Cart> completePurchase(@RequestBody @Validated Cart cart) throws QuantityProductUnavailableException, ProductNotFoundException, CartNotFoundException {
        Cart updatedCart = cartService.completePurchase(cart);
        return new ResponseEntity<>(updatedCart,HttpStatus.OK);
    }

    @PutMapping("/additem")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody @Validated CartItem cartItem) throws CartNotFoundException, ProductNotFoundException {
        cartService.addItemToCart(cartItem);
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }

    @PutMapping("/removeitems")
    public ResponseEntity<CartItem> removeItemsFromCart(@RequestBody CartItem cartItem) throws CartItemNotFoundException, QuantityProductUnavailableException {
        CartItem ct =  cartService.deleteItemsFromCart(cartItem);
        return new ResponseEntity<>(ct,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCart(@RequestParam Long id) throws CartNotFoundException {
        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
