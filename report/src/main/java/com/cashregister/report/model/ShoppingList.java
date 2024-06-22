
package com.cashregister.report.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="sales_info")
@Getter
@Setter
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salesInfo")
    private List<ShoppingItem> saleItemList;


    @ManyToOne
    @JoinColumn(name = "campaign_id",referencedColumnName = "id")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private double totalPrice;

    private Date saleDate;



    //@Column(name = "total_price")
    //private double totalPrice;
}
