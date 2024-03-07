package com.cashregister.sale.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemRequestDto {


    private int quantity;
    private String type;

    private long id;

}
