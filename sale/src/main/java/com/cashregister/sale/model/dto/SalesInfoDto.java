package com.cashregister.sale.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SalesInfoDto {

   private String  cashierName;
   private String cashierSurname;
   private double totalPrice;
   private Date saleDate;

   private List<SaleItemResponseDto> saleItemResponseDtoList=new ArrayList<>();




}
