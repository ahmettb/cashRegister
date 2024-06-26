package com.cashregister.sale.service;

import com.cashregister.sale.exception.SaleListNotFound;
import com.cashregister.sale.feignclient.AuthClient;
import com.cashregister.sale.feignclient.ProductClient;
import com.cashregister.sale.model.*;
import com.cashregister.sale.model.dto.CreateSaleResponse;
import com.cashregister.sale.model.dto.SaleItemResponseDto;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.model.dto.ShoppingItemRequestDto;
import com.cashregister.sale.repository.IShoppingItemRepository;
import com.cashregister.sale.repository.IShoppingListRepository;
import com.cashregister.sale.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class SaleServiceImp implements ISalesService {

    private final IShoppingListRepository shoppingListRepository;
    private final IShoppingItemRepository shoppingItemRepository;
    private final AuthClient authClient;
    private final ProductClient productClient;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public SalesInfoDto getSaleInfo(long id) {
        log.info("SaleServiceImp: getSaleInfo method called with id = {}", id);
        ShoppingList shoppingList = shoppingListRepository.findById(id).orElseThrow(() -> new SaleListNotFound("Sales List with " + id + " ID not found"));
        SalesInfoDto salesInfoDto = new SalesInfoDto();
        salesInfoDto.setCashierName(shoppingList.getUser().getName());
        salesInfoDto.setCashierSurname(shoppingList.getUser().getSurname());
        salesInfoDto.setSaleDate(shoppingList.getSaleDate());

        shoppingList.getSaleItemList().forEach(shoppingItem -> {
            SaleItemResponseDto saleItemResponseDto = new SaleItemResponseDto();
            saleItemResponseDto.setPrice(shoppingItem.getPrice());
            saleItemResponseDto.setName(shoppingItem.getProduct().getName());
            saleItemResponseDto.setType(shoppingItem.getType());
            saleItemResponseDto.setCount(shoppingItem.getQuantity());
            saleItemResponseDto.setTotalPrice(shoppingItem.getTotalPrice());
            salesInfoDto.getSaleItemResponseDtoList().add(saleItemResponseDto);
            salesInfoDto.setTotalPrice(salesInfoDto.getTotalPrice() + saleItemResponseDto.getTotalPrice());
        });

        log.info("SaleServiceImp: getSaleInfo method completed for id = {}", id);
        return salesInfoDto;
    }

    @Override
    public SalesInfoDto getSaleInfoById(long saleId) {
        log.info("SaleServiceImp: getSaleInfoById method called with saleId = {}", saleId);
        SalesInfoDto salesInfoDto = getSaleInfo(saleId);
        log.info("SaleServiceImp: getSaleInfoById method completed for saleId = {}", saleId);
        return salesInfoDto;
    }

    @Transactional
    @Override
    public SalesInfoDto addShoppingItemToList(List<ShoppingItemRequestDto> requestDtos, long shoppingId, String token) {
        log.info("SaleServiceImp: addShoppingItemToList method called with shoppingId = {}", shoppingId);
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingId).orElseThrow(() -> new SaleListNotFound("Sale list not found"));

        requestDtos.forEach(itemRequestDto -> {
            Product product = productClient.getProductById(itemRequestDto.getId()).getBody();
            boolean productControl = shoppingList.getSaleItemList().stream().anyMatch(item -> item.getProduct().getId() == itemRequestDto.getId());

            if (shoppingList.getSaleItemList().isEmpty()) {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setProduct(product);
                shoppingItem.setPrice(product.getPrice());
                shoppingItem.setQuantity(itemRequestDto.getQuantity());
                shoppingItem.setType(itemRequestDto.getType());
                shoppingItem.setSalesInfo(shoppingList);
                shoppingItem.setTotalPrice(product.getPrice() * itemRequestDto.getQuantity());
                productClient.updateStock(product.getId(), itemRequestDto.getQuantity());
                shoppingList.getSaleItemList().add(shoppingItem);
                shoppingItemRepository.save(shoppingItem);
            } else {
                if (productControl) {
                    ShoppingItem shoppingItem = shoppingList.getSaleItemList().stream().filter(item -> item.getProduct().getId() == itemRequestDto.getId()).findFirst().get();
                    shoppingItem.setQuantity(shoppingItem.getQuantity() + itemRequestDto.getQuantity());
                    shoppingItem.setTotalPrice(shoppingItem.getTotalPrice() + itemRequestDto.getQuantity() * product.getPrice());
                    productClient.updateStock(product.getId(), itemRequestDto.getQuantity());
                    shoppingItemRepository.save(shoppingItem);
                } else {
                    ShoppingItem shoppingItem = new ShoppingItem();
                    shoppingItem.setProduct(product);
                    shoppingItem.setPrice(product.getPrice());
                    shoppingItem.setSalesInfo(shoppingList);
                    shoppingItem.setQuantity(itemRequestDto.getQuantity());
                    shoppingItem.setType(itemRequestDto.getType());
                    shoppingItem.setTotalPrice(itemRequestDto.getQuantity() * product.getPrice());
                    productClient.updateStock(product.getId(), itemRequestDto.getQuantity());
                    shoppingList.getSaleItemList().add(shoppingItem);
                    shoppingItemRepository.save(shoppingItem);
                }
            }
        });

        shoppingList.setSaleDate(new Date());
        SalesInfoDto salesInfoDto = getSaleInfo(shoppingId);
        log.info("SaleServiceImp: addShoppingItemToList method completed for shoppingId = {}", shoppingId);
        return salesInfoDto;
    }

    @Override
    public CreateSaleResponse createSale(String cashierToken) {
        log.info("SaleServiceImp: createSale method called with cashierToken");
        ShoppingList shoppingList = new ShoppingList();
        UserInfo userInfo = authClient.getUserInfo(cashierToken).getBody();
        shoppingList.setUser(userRepository.getUserById(userInfo.getId()));
        shoppingListRepository.save(shoppingList);

        CreateSaleResponse createSaleResponse=new CreateSaleResponse();
        createSaleResponse.setCashierName(userInfo.getName());
        createSaleResponse.setCashierSurname(userInfo.getSurname());
        createSaleResponse.setSaleId(shoppingList.getId());
        createSaleResponse.setInfoMessage("New sale record created");
        log.info("SaleServiceImp: createSale method completed with new shoppingList created");
        return createSaleResponse;
    }
}
