package it.francesco.ecommerceserver.controllers;

import it.francesco.ecommerceserver.support.exceptions.CartAlreadyInRepoException;
import it.francesco.ecommerceserver.model.User;
import it.francesco.ecommerceserver.services.UserService;
import it.francesco.ecommerceserver.support.exceptions.CartNotFoundException;
import it.francesco.ecommerceserver.support.exceptions.UserAlreadyRegisteredException;
import it.francesco.ecommerceserver.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<User> getUserByEmailAndPassword(@RequestParam(defaultValue = "empty") String email,
                                                          @RequestParam(defaultValue = "empty") String password) throws UserNotFoundException {
        User user = userService.findUserByEmailAndPassword(email,password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody @Validated User user) throws UserAlreadyRegisteredException, CartAlreadyInRepoException, CartNotFoundException {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody @Validated User user) throws UserNotFoundException {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam(defaultValue = "empty") Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}