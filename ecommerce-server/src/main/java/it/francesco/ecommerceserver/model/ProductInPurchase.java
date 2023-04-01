package it.francesco.ecommerceserver.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_in_purchase")
public class ProductInPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase")
    @JsonIgnore
    private Purchase purchase;

    @Basic
    @Column(name = "quantity", nullable = true)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    public ProductInPurchase(Purchase purchase, int quantity, Product product){
        this.product = product;
        this.purchase = purchase;
        this.quantity = quantity;
    }
}
