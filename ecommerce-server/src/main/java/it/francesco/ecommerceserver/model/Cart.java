package it.francesco.ecommerceserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "related_user")
    private User user;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(User user, List<CartItem> cartItems){
        this.user = user;
        this.cartItems = cartItems;
    }
}
