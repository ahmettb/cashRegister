package com.cashregister.sale.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {


    private String name;
    private double price;
    private String description;
    private int stockCount;
    private String category;
    private String pieceOrKg;




}
