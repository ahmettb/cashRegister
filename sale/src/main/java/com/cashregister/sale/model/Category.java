package com.cashregister.sale.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String categoryName;

//    @OneToMany(mappedBy = "category")
//    @JsonBackReference
//    private List<Product> productList;
}
