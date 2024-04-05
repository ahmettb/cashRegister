package com.cashregister.sale.service;

//import com.cashregister.product.model.Product;
//import com.cashregister.product.repository.IProductRepository;

import com.cashregister.sale.model.*;
import com.cashregister.sale.model.dto.SaleItemRequestDto;
import com.cashregister.sale.model.dto.SaleItemResponseDto;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.repository.ISaleItemRepository;
import com.cashregister.sale.repository.ISalesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SaleServiceImp implements ISalesService {

    private RestTemplate restTemplate;
    final ISalesRepository salesRepository;
    final ISaleItemRepository saleItemRepository;


    @Override
    public void getAllSales() {

    }

    @Override
    public SalesInfoDto addSaleItem(List<SaleItemRequestDto> saleItemDto, long saleId) {

        SalesInfoDto salesInfoResp=new SalesInfoDto();



      SalesInfo salesInfo=  salesRepository.findById(saleId).orElseThrow(() -> new RuntimeException());
      List<SaleItem>saleItems=new ArrayList<>();

        saleItemDto.forEach(saleItemDto1 ->
                {
                    ResponseEntity<Product> responseProduct = restTemplate.getForEntity(
                            "http://localhost:8080/api/product/getProductById/" + saleItemDto1.getId(), Product.class);

                    Product product = responseProduct.getBody();
                    SaleItem saleItem=new SaleItem();
                    saleItem.setProduct(product);
                    saleItem.setQuantity(saleItemDto1.getQuantity());
                    saleItem.setType("kg");
                    saleItem.setSalesInfo(salesInfo);
                    saleItem.setTotalPrice(product.getPrice()*saleItemDto1.getQuantity());
                    saleItemRepository.save(saleItem);
                    saleItems.add(saleItem);
                    salesInfo.getSaleItemList().add(saleItem);

                }

        );
        salesInfo.getSaleItemList().forEach(
                saleItem ->
                {
                    SaleItemResponseDto saleItemResponseDto=new SaleItemResponseDto();
                    saleItemResponseDto.setCount(saleItem.getQuantity());
                    saleItemResponseDto.setType(saleItem.getType());
                    saleItemResponseDto.setProductName(saleItem.getProduct().getName());
                    saleItemResponseDto.setProductDescription(saleItem.getProduct().getDescription());
                    saleItemResponseDto.setProductPrice(saleItem.getProduct().getPrice());
                    saleItemResponseDto.setTotalPrice(saleItem.getQuantity()*saleItem.getProduct().getPrice());
                    salesInfoResp.getSaleItemResponseDtoList().add(saleItemResponseDto);
                    salesInfoResp.setTotalPrice(salesInfoResp.getTotalPrice()+saleItem.getTotalPrice());

                }

        );


        salesRepository.save(salesInfo);

        return salesInfoResp;





    }

    @Override
    public void addSaleInfo() {

        salesRepository.save(new SalesInfo());

    }

    @Override
    public SalesInfoDto getSaleInfoById(long saleId) {
        SalesInfo salesInfo=  salesRepository.findById(saleId).orElseThrow(() -> new RuntimeException());
        SalesInfoDto salesInfoResp=new SalesInfoDto();

        salesInfo.getSaleItemList().forEach(
                saleItem ->
                {
                    SaleItemResponseDto saleItemResponseDto=new SaleItemResponseDto();
                    saleItemResponseDto.setCount(saleItem.getQuantity());
                    saleItemResponseDto.setType(saleItem.getType());
                    saleItemResponseDto.setProductName(saleItem.getProduct().getName());
                    saleItemResponseDto.setProductDescription(saleItem.getProduct().getDescription());
                    saleItemResponseDto.setProductPrice(saleItem.getProduct().getPrice());
                    saleItemResponseDto.setTotalPrice(saleItem.getQuantity()*saleItem.getProduct().getPrice());
                    salesInfoResp.getSaleItemResponseDtoList().add(saleItemResponseDto);
                    salesInfoResp.setTotalPrice(salesInfoResp.getTotalPrice()+saleItem.getTotalPrice());

                }

        );

        return  salesInfoResp;





    }

    @Override
    public List<SalesInfo> getSaleById(long saleId) {

        return salesRepository.findAll();

    }
}
