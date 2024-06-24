package com.cashregister.report.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemResponseDto {


    private String name;

    private double price;


    private double quantity;

    private double totalPrice;

    private String type;
}
