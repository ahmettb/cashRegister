package com.cashregister.sale.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sale_item")
    public class ShoppingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sales_info_id", referencedColumnName = "id")
    @JsonBackReference
    private ShoppingList salesInfo;

    private double quantity;
    private String type;
    private double totalPrice;
}
