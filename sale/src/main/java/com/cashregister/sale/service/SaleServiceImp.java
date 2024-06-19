package com.cashregister.sale.service;

//import com.cashregister.product.model.Product;
//import com.cashregister.product.repository.IProductRepository;

import com.cashregister.sale.config.ModelMapperConfig;
import com.cashregister.sale.error.ExceptionMessage;
import com.cashregister.sale.exception.SaleListNotFound;
import com.cashregister.sale.feignclient.ProductClient;
import com.cashregister.sale.model.*;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.model.dto.ShoppingItemRequestDto;

import com.cashregister.sale.repository.IShoppingItemRepository;
import com.cashregister.sale.repository.IShoppingListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SaleServiceImp implements ISalesService {

    private RestTemplate restTemplate;
    final IShoppingListRepository shoppingListRepository;
    final IShoppingItemRepository shoppingItemRepository;
    final ProductClient productClient;
    final ModelMapperConfig mapperConfig;



    @Override
    public void getAllSales() {

    }

    @Override
    public List<ShoppingItem> getSaleById(long saleId) {
        return List.of();
    }

    @Override
    public ShoppingList getSaleInfoById(long saleId) {

      ShoppingList shoppingList= shoppingListRepository.findById(saleId).orElseThrow(()-> new SaleListNotFound("Sales List with "+saleId+" ID not found"));
return  shoppingList;
    }


    @Override
    public void saveSaleItem(ShoppingItemRequestDto saleItemRequestDto) {

    }

    @Override
    public void addShoppingList(ShoppingItemRequestDto saleItemRequestDto) {

    }

    @Override
    public void addShoppingItemToList(ShoppingItemRequestDto itemRequestDto) {

    }

    @Override
    public SalesInfoDto addSaleItem(List<ShoppingItemRequestDto> saleItemDto, long saleId) {
        return null;
    }

    @Override
    public void addSaleInfo() {

    }

    @Override
    public void removeItemFromList(long shoppingId, long itemId) {

    }

    @Override
    public ShoppingList addShoppingItemToList(ShoppingItemRequestDto itemRequestDto, long shoppingId) {

    ShoppingList shoppingList = new ShoppingList();

        if(!shoppingListRepository.findById(shoppingId).isPresent())
        {
            shoppingList=new ShoppingList();
            shoppingListRepository.save(shoppingList);
        }



            shoppingList=shoppingListRepository.findById(shoppingId).get();

            Product product=productClient.getProductById(itemRequestDto.getId()).getBody();

            productClient.updateStock(product.getId(),itemRequestDto.getQuantity());

            ShoppingItem shoppingItem=new ShoppingItem();
            shoppingItem.setProduct(product);
            shoppingItem.setSalesInfo(shoppingList);
            shoppingItem.setQuantity(itemRequestDto.getQuantity());
            shoppingItem.setType(itemRequestDto.getType());
            shoppingItem.setTotalPrice(itemRequestDto.getQuantity()*product.getPrice());

            shoppingList.getSaleItemList().add(shoppingItem);
            shoppingItemRepository.save(shoppingItem);

            return  shoppingList;
        }






}
