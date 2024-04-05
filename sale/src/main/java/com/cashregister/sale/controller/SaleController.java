package com.cashregister.sale.controller;


import com.cashregister.sale.model.dto.SaleItemRequestDto;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.service.ISalesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sale")
@AllArgsConstructor
public class SaleController {

    final ISalesService salesService;


    @PostMapping("addSaleItem/{saleId}")
    public ResponseEntity <SalesInfoDto>add(@RequestBody List<SaleItemRequestDto> saleItemDto, @PathVariable("saleId") long saleId)
    {

        SalesInfoDto salesInfo= salesService.addSaleItem(saleItemDto,saleId);
        return new ResponseEntity<>(salesInfo,HttpStatus.OK);

    }
    @PostMapping("addSaleInfo")
    public ResponseEntity <?>addInfo()
    {

     salesService.addSaleInfo();
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping("getSaleInfoById/{id}")
    public ResponseEntity <SalesInfoDto>addInfo(@PathVariable("id") long id)
    {

            SalesInfoDto salesInfoDto=salesService.getSaleInfoById(id);
        return new ResponseEntity<>(salesInfoDto,HttpStatus.OK);

    }

}
