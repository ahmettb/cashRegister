package com.cashregister.sale.service;

import com.cashregister.sale.model.*;

import java.util.List;



public interface ISalesService {


    void getAllSales();


    SalesInfoDto addSaleItem(List<SaleItemRequestDto> saleItemDto, long saleId);
    void addSaleInfo();

    SalesInfoDto getSaleInfoById(long saleId);


    List<SalesInfo> getSaleById(long saleId);


}
