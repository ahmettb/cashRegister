package com.cashregister.sale.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemResponseDto {
    private String productName;

    private double productPrice;

    private String productDescription;

    private double count;

    private double totalPrice;

    private String type;
}
