package com.cashregister.product.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWithCategoryDto {

    private String name;
    private double price;
    private  String description;
    private  long categoryId;



}
