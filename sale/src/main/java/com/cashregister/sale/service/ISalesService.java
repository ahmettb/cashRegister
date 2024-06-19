package com.cashregister.sale.service;

import com.cashregister.sale.model.*;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.model.dto.ShoppingItemRequestDto;

import java.util.List;



public interface ISalesService {


    void getAllSales();

    List<ShoppingItem> getSaleById(long saleId);
    ShoppingList getSaleInfoById(long saleId);

    void saveSaleItem(ShoppingItemRequestDto saleItemRequestDto);
    void addShoppingList(ShoppingItemRequestDto saleItemRequestDto);


    void addShoppingItemToList(ShoppingItemRequestDto itemRequestDto);

    SalesInfoDto addSaleItem(List<ShoppingItemRequestDto> saleItemDto, long saleId);
    void addSaleInfo();



    void removeItemFromList(long shoppingId,long itemId);
    ShoppingList addShoppingItemToList(ShoppingItemRequestDto itemRequestDto, long shoppingId);
}
