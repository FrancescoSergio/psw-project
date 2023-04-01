package it.francesco.ecommerceserver.services;

import it.francesco.ecommerceserver.repositories.*;
import it.francesco.ecommerceserver.support.exceptions.*;
import it.francesco.ecommerceserver.model.*;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final UserRepository userRepository;

    private final PurchaseService purchaseService;

    private final CartsRepository cartsRepository;

    private final ProductsRepository productsRepository;
    
    private final CartItemsRepository cartItemsRepository;

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public CartService(UserRepository userRepository, PurchaseService purchaseService, CartsRepository cartsRepository, ProductsRepository productsRepository, CartItemsRepository cartItemsRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.purchaseService = purchaseService;
        this.cartsRepository = cartsRepository;
        this.productsRepository = productsRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.purchaseRepository = purchaseRepository;
    }


    public Cart addToCart(List<CartItem> cartItems, Long cartId){
        List<CartItem> newCartItems = new ArrayList<>();
        for(CartItem ci : cartItems){
            if(cartItemsRepository.existsByProduct_IdAndCart_Id(ci.getProduct().getId(),ci.getCart().getId())) {
                CartItem temp = cartItemsRepository.findByProduct_IdAndCart_Id(ci.getProduct().getId(), ci.getCart().getId());
                temp.setQuantity(ci.getQuantity() + temp.getQuantity());
                cartItemsRepository.save(temp);
                newCartItems.add(temp);
            }else {
                cartItemsRepository.save(ci);
                newCartItems.add(ci);
            }
        }
        Cart newCart = new Cart(cartId,cartsRepository.findCartById(cartId).getUser(),newCartItems);
        return cartsRepository.save(newCart);
    }

    @Transactional(readOnly = false)
    public void addItemToCart( CartItem cartItem) throws CartNotFoundException, ProductNotFoundException {
        if(!cartsRepository.existsById(cartItem.getCart().getId())){
            throw new CartNotFoundException();
        }
        if(!productsRepository.existsByName(cartItem.getProduct().getName())){
            throw new ProductNotFoundException();
        }
        List<CartItem> nuovaList = cartItem.getCart().getCartItems();
        nuovaList.add(cartItem);
        addToCart(nuovaList,cartItem.getCart().getId());
    }

    @Transactional(readOnly = false)
    public Cart completePurchase(Cart cart) throws CartNotFoundException, ProductNotFoundException, QuantityProductUnavailableException, OptimisticLockException {
        if(!cartsRepository.existsById(cart.getId())){
            throw new CartNotFoundException();
        }
        List<ProductInPurchase> pips = new ArrayList<>();
        Purchase purchase = new Purchase(cart.getUser(), pips);
        purchase = purchaseRepository.save(purchase);
        for(CartItem ci : cartItemsRepository.findByCart(cart)){
            if(!productsRepository.existsById(ci.getProduct().getId())) {
                throw new ProductNotFoundException();
            }
            ProductInPurchase pip = new ProductInPurchase(purchase,ci.getQuantity(),ci.getProduct());
            pips.add(pip);
        }
        purchase.setProductsInPurchase(pips);
        purchaseService.addPurchase(purchase);
        List<CartItem> emptyList = new ArrayList<>();
        Cart newCart = cartsRepository.findCartById(cart.getId());
        cartItemsRepository.deleteAllByCart_Id(newCart.getId());
        newCart.setCartItems(emptyList);
        return cartsRepository.save(newCart);
    }


    @Transactional(readOnly = false)
    public Cart addCart(Cart cart) throws CartAlreadyInRepoException {
        if(cartsRepository.existsByUser(cart.getUser())){
            throw new CartAlreadyInRepoException();
        }
        return cartsRepository.save(cart);

    }

    @Transactional(readOnly = true)
    public List<Cart> findAllCarts(){
        return cartsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cart findCartById(Long id) throws CartNotFoundException {
        if(cartsRepository.existsById(id)){
            return cartsRepository.findCartById(id);
        }
        throw new CartNotFoundException();
    }

    @Transactional(readOnly = true)
    public Cart findCartByUser(String email) throws CartNotFoundException, UserNotFoundException {
        if(!userRepository.existsByEmail(email)){
            throw new UserNotFoundException();
        }
        User user = userRepository.findByEmail(email);
        if(cartsRepository.existsByUser(user)){
            return cartsRepository.findCartByUser(user);
        }
        throw new CartNotFoundException();
    }

    @Transactional(readOnly = false)
    public void deleteCart(Long id) throws CartNotFoundException {
        if(cartsRepository.existsById(id)) {
            cartsRepository.deleteCartById(id);
        }
        throw new CartNotFoundException();
    }

    @Transactional(readOnly = true)
    public List<CartItem> findItemsByCartId(Long id) throws CartNotFoundException {
        if(!cartsRepository.existsById(id)){
            throw new CartNotFoundException();
        }
        return cartItemsRepository.findByCart_Id(id);
    }

    @Transactional(readOnly = false)
    public CartItem deleteItemsFromCart(CartItem cartItem) throws CartItemNotFoundException, QuantityProductUnavailableException {
        if(!cartItemsRepository.existsById(cartItem.getId())){
            throw new CartItemNotFoundException();
        }
        CartItem ref = cartItemsRepository.findCartItemById(cartItem.getId());
        if(cartItem.getQuantity()>=ref.getQuantity()){
            cartItemsRepository.deleteById(ref.getId());
        } else if (cartItem.getQuantity()<ref.getQuantity()&&cartItem.getQuantity()>=0) {
            int newQuantity = ref.getQuantity() - cartItem.getQuantity();
            ref.setQuantity(newQuantity);
            cartItemsRepository.save(ref);
        } else{
            throw new QuantityProductUnavailableException();
        }
        return cartItemsRepository.findCartItemById(ref.getId());
    }
}
