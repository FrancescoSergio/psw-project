package it.francesco.ecommerceserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer")
    private User buyer;

    @OneToMany(mappedBy = "purchase")
    private List<ProductInPurchase> productsInPurchase = new ArrayList<>();

    public Purchase(User buyer, List<ProductInPurchase> pips){
        this.buyer = buyer;
        this.productsInPurchase = pips;
    }
}
