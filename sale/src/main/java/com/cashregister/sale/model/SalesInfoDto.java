package com.cashregister.sale.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SalesInfoDto {



    private long id;


   private List<SaleItemResponseDto> saleItemResponseDtoList=new ArrayList<>();

   private double totalPrice;



}
