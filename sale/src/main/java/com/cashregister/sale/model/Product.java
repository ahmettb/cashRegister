
package com.cashregister.sale.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private double price;
    private String description;
    private int stockCount;
    private boolean deleted=false;


    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    //@JsonManagedReference
    private Category category;


    // Diğer alanlar ve getter/setterlar...
}
