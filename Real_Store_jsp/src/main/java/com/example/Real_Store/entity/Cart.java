package com.example.Real_Store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long cartId;
    private Long userId;
    private Long productId;
    private int requiredQuantity;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<Product> productList;


}
