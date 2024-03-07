
package com.cashregister.sale.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="sales_info")
@Getter
@Setter
public class SalesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salesInfo")
    private List<SaleItem> saleItemList;
}
