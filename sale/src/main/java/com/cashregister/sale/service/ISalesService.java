package com.cashregister.sale.service;

import com.cashregister.sale.model.*;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.model.dto.ShoppingItemRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



public interface ISalesService {




    SalesInfoDto getSaleInfoById(long saleId);




    @Transactional
    SalesInfoDto addShoppingItemToList(List<ShoppingItemRequestDto > itemRequestDto, long shoppingId,String token);

    ShoppingList createSale(String cashierToken);


}


