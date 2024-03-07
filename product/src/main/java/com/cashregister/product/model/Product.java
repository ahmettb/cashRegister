
package com.cashregister.product.model;

import com.cashregister.sale.model.SalesInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    @JsonManagedReference
    private Category category;

   // @ManyToMany(mappedBy = "productList")
   // private List<SalesInfo> saleList;

}
