package com.example.Real_Store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String password;
    private String mobile;
    private String address;
    private String email;


//    @JsonManagedReference
//    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
//    public List<Booking> orderList;
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
//    @JoinTable(name = "product-Customer",
//            joinColumns = @JoinColumn(name = "customerId"),
//            inverseJoinColumns = @JoinColumn(name = "productId") )
//    public List<Product> productList;
}
