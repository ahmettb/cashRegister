package com.cashregister.sale.service;

import com.cashregister.sale.model.*;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.model.dto.ShoppingItemRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



public interface ISalesService {


    void getAllSales();

    List<ShoppingItem> getSaleById(long saleId);
    SalesInfoDto getSaleInfoById(long saleId);

    void saveSaleItem(ShoppingItemRequestDto saleItemRequestDto);
    void addShoppingList(ShoppingItemRequestDto saleItemRequestDto);


    void addShoppingItemToList(ShoppingItemRequestDto itemRequestDto);

    SalesInfoDto addSaleItem(List<ShoppingItemRequestDto> saleItemDto, long saleId);
    void addSaleInfo();



    void removeItemFromList(long shoppingId,long itemId);

    @Transactional
    SalesInfoDto addShoppingItemToList(List<ShoppingItemRequestDto > itemRequestDto, long shoppingId,String token);

    ShoppingList createSale(String cashierToken);


}


