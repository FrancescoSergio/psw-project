package it.francesco.ecommerceserver.services;

import it.francesco.ecommerceserver.repositories.CartsRepository;
import it.francesco.ecommerceserver.support.exceptions.CartAlreadyInRepoException;
import it.francesco.ecommerceserver.model.Cart;
import it.francesco.ecommerceserver.model.CartItem;
import it.francesco.ecommerceserver.model.User;
import it.francesco.ecommerceserver.repositories.UserRepository;
import it.francesco.ecommerceserver.support.exceptions.CartNotFoundException;
import it.francesco.ecommerceserver.support.exceptions.UserAlreadyRegisteredException;
import it.francesco.ecommerceserver.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final CartsRepository cartsRepository;

    private final CartService cartService;

    private final UserRepository userRepository;

    @Autowired
    public UserService(CartsRepository cartsRepository, CartService cartService, UserRepository userRepository) {
        this.cartsRepository = cartsRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = false)
    public User addUser(User user) throws UserAlreadyRegisteredException, CartAlreadyInRepoException, CartNotFoundException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyRegisteredException();
        }
        List<CartItem> emptyList = new ArrayList<>();
        User result = userRepository.save(user);
        Cart newCart = new Cart(result,emptyList);
        cartsRepository.save(newCart);
        return result;
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) throws UserNotFoundException {
        if(userRepository.existsById(id)){
            return userRepository.findUserById(id);
        }else{
            throw new UserNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public User findUserByEmailAndPassword(String email,String password) throws UserNotFoundException {
        if(userRepository.existsByEmail(email)){
            return userRepository.findUserByEmailAndPassword(email,password);
        }else{
            throw new UserNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = false)
    public User updateUser(User user) throws UserNotFoundException {
        if(userRepository.existsById(user.getId())){
            return userRepository.save(user);
        }
        else{
            throw new UserNotFoundException();
        }
    }

    @Transactional(readOnly = false)
    public void deleteUser(Long id) throws UserNotFoundException{
        if(userRepository.existsById(id)) {
            userRepository.deleteUserById(id);
        }
        else{
            throw new UserNotFoundException();
        }
    }

}