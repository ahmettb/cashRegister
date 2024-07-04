package com.cashregister.sale.controller;


import com.cashregister.sale.error.ExceptionMessage;
import com.cashregister.sale.exception.NotFoundProduct;
import com.cashregister.sale.exception.StockNotEnough;
import com.cashregister.sale.model.ShoppingList;
import com.cashregister.sale.model.dto.CreateSaleResponse;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.model.dto.ShoppingItemRequestDto;
import com.cashregister.sale.service.ISalesService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("api/sale")
@AllArgsConstructor
public class SaleController {

    final ISalesService salesService;

    @GetMapping("get-sale-info/{id}")
    public ResponseEntity<SalesInfoDto> getSaleInfo(@PathVariable("id") long id) {


        return new ResponseEntity<>(salesService.getSaleInfoById(id), HttpStatus.OK);

    }

    @PostMapping("add-item-to-card/{id}")
    public ResponseEntity<?> addShoppingItemToList(@RequestHeader("Authorization")String token ,@RequestBody List<ShoppingItemRequestDto > shoppingItemDto, @PathVariable("id") long id) {
        token=token.substring(7);
        return new ResponseEntity<>(salesService.addShoppingItemToList(shoppingItemDto, id,token), HttpStatus.OK);

    }

    @PostMapping("create-sale")
    public ResponseEntity<CreateSaleResponse> createSale(@RequestHeader("Authorization")String token) {
        return new ResponseEntity<>(salesService.createSale(token), HttpStatus.OK);

    }


}
