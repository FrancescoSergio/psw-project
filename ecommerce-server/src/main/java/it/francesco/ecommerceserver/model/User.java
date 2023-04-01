package it.francesco.ecommerceserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "id",nullable = false, updatable = false)
    private Long id;

    @Basic
    @Column(name = "email",nullable = false)
    private String email;

    @Basic
    @Column(name = "password",nullable = false)
    private String password;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Cart cart;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
}