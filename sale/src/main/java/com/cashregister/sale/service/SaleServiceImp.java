package com.cashregister.sale.service;

//import com.cashregister.product.model.Product;
//import com.cashregister.product.repository.IProductRepository;

import com.cashregister.sale.config.ModelMapperConfig;
import com.cashregister.sale.exception.SaleListNotFound;
import com.cashregister.sale.feignclient.AuthClient;
import com.cashregister.sale.feignclient.ProductClient;
import com.cashregister.sale.model.*;
import com.cashregister.sale.model.dto.SaleItemResponseDto;
import com.cashregister.sale.model.dto.SalesInfoDto;
import com.cashregister.sale.model.dto.ShoppingItemRequestDto;

import com.cashregister.sale.repository.IShoppingItemRepository;
import com.cashregister.sale.repository.IShoppingListRepository;
import com.cashregister.sale.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SaleServiceImp implements ISalesService {

    private final ModelMapper modelMapper;
    private RestTemplate restTemplate;
    final IShoppingListRepository shoppingListRepository;
    final IShoppingItemRepository shoppingItemRepository;
    final AuthClient authClient;
    final ProductClient productClient;
    final ModelMapperConfig mapperConfig;
    final IUserRepository userRepository;


    @Override
    public void getAllSales() {

    }

    @Override
    public List<ShoppingItem> getSaleById(long saleId) {
        return List.of();
    }


    public SalesInfoDto getSaleInfo(long id)
    {
        ShoppingList shoppingList = shoppingListRepository.findById(id).orElseThrow(() -> new SaleListNotFound("Sales List with " + id + " ID not found"));
        SalesInfoDto salesInfoDto = new SalesInfoDto();
        salesInfoDto.setCashierName(shoppingList.getUser().getName());
        salesInfoDto.setCashierSurname(shoppingList.getUser().getSurname());
        salesInfoDto.setSaleDate(shoppingList.getSaleDate());
        double totalPrice=0;



        shoppingList.getSaleItemList().forEach(shoppingItem ->
        {
            SaleItemResponseDto saleItemResponseDto = new SaleItemResponseDto();
            saleItemResponseDto.setPrice(shoppingItem.getPrice());
            saleItemResponseDto.setName(shoppingItem.getProduct().getName());
            saleItemResponseDto.setType(shoppingItem.getType());
            saleItemResponseDto.setCount(shoppingItem.getQuantity());
            saleItemResponseDto.setTotalPrice(shoppingItem.getTotalPrice());
            salesInfoDto.getSaleItemResponseDtoList().add(saleItemResponseDto);
            salesInfoDto.setTotalPrice(salesInfoDto.getTotalPrice()+saleItemResponseDto.getTotalPrice());

        });


        return salesInfoDto;

    }

    @Override
    public SalesInfoDto getSaleInfoById(long saleId) {


        return getSaleInfo(saleId);

        //  SalesInfoDto salesInfoDto =modelMapper.map(shoppingList,SalesInfoDto.class);
        //return salesInfoDto;


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

    @Transactional
    @Override
    public SalesInfoDto addShoppingItemToList(List<ShoppingItemRequestDto >requestDtos,long shoppingId, String token) {

        ShoppingList shoppingList=  shoppingListRepository.findById(shoppingId).orElseThrow(()->new SaleListNotFound("Sale list not found"));

        requestDtos.forEach(itemRequestDto->
        {

            Product product= productClient.getProductById(itemRequestDto.getId()).getBody();
            boolean productControl=shoppingList.getSaleItemList().stream().anyMatch(item->item.getProduct().getId()==itemRequestDto.getId());


            if(shoppingList.getSaleItemList().isEmpty()) {


                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setProduct(product);
                shoppingItem.setPrice(product.getPrice());
                shoppingItem.setQuantity(itemRequestDto.getQuantity());
                shoppingItem.setType(itemRequestDto.getType());
                shoppingItem.setSalesInfo(shoppingList);
                shoppingItem.setTotalPrice(product.getPrice()*itemRequestDto.getQuantity());
                productClient.updateStock(product.getId(),itemRequestDto.getQuantity());

                shoppingList.getSaleItemList().add(shoppingItem);
                shoppingItem.setSalesInfo(shoppingList);
                //  shoppingListRepository.save(shoppingList);
                shoppingItemRepository.save(shoppingItem);
            }
            else {

                if(productControl) {

                    ShoppingItem shoppingItem=  shoppingList.getSaleItemList().stream().filter(item->item.getProduct().getId()==itemRequestDto.getId()).findFirst().get();

                    shoppingItem.setQuantity(shoppingItem.getQuantity()+itemRequestDto.getQuantity());
                    shoppingItem.setTotalPrice(shoppingItem.getTotalPrice()+itemRequestDto.getQuantity()*product.getPrice());
                    productClient.updateStock(product.getId(),itemRequestDto.getQuantity());
                    shoppingItem.setTotalPrice(itemRequestDto.getQuantity() * product.getPrice());

                    shoppingItemRepository.save(shoppingItem);

                }
                else {

                    ShoppingItem shoppingItem = new ShoppingItem();
                    shoppingItem.setProduct(product);
                    shoppingItem.setPrice(product.getPrice());

                    shoppingItem.setSalesInfo(shoppingList);
                    shoppingItem.setQuantity(itemRequestDto.getQuantity());
                    shoppingItem.setType(itemRequestDto.getType());
                    shoppingItem.setTotalPrice(itemRequestDto.getQuantity() * product.getPrice());
                    productClient.updateStock(product.getId(),itemRequestDto.getQuantity());

                    shoppingList.getSaleItemList().add(shoppingItem);
                    shoppingItemRepository.save(shoppingItem);
                    //shoppingListRepository.save(shoppingList);

                }




            }
        });
shoppingList.setSaleDate(new Date());


SalesInfoDto salesInfoDto=getSaleInfo(shoppingId);

return salesInfoDto;






//      ShoppingList shoppingList=  shoppingListRepository.findById(shoppingId).orElseThrow(()->new SaleListNotFound("Sale list not found"));
//       Product product= productClient.getProductById(itemRequestDto.getId()).getBody();
//        boolean productControl=shoppingList.getSaleItemList().stream().anyMatch(item->item.getProduct().getId()==itemRequestDto.getId());
//
//
//        if(shoppingList.getSaleItemList().isEmpty()) {
//
//
//            ShoppingItem shoppingItem = new ShoppingItem();
//            shoppingItem.setProduct(product);
//            shoppingItem.setQuantity(itemRequestDto.getQuantity());
//            shoppingItem.setType(itemRequestDto.getType());
//            shoppingItem.setSalesInfo(shoppingList);
//            shoppingItem.setTotalPrice(product.getPrice()*itemRequestDto.getQuantity());
//            shoppingList.getSaleItemList().add(shoppingItem);
//            shoppingItem.setSalesInfo(shoppingList);
//          //  shoppingListRepository.save(shoppingList);
//            shoppingItemRepository.save(shoppingItem);
//            productClient.updateStock(product.getId(),itemRequestDto.getQuantity());
//            return shoppingList;
//        }
//        else {
//
//            if(productControl) {
//
//              ShoppingItem shoppingItem=  shoppingList.getSaleItemList().stream().filter(item->item.getProduct().getId()==itemRequestDto.getId()).findFirst().get();
//
//              shoppingItem.setQuantity(shoppingItem.getQuantity()+itemRequestDto.getQuantity());
//              shoppingItem.setTotalPrice(shoppingItem.getTotalPrice()+itemRequestDto.getQuantity()*product.getPrice());
//                productClient.updateStock(product.getId(),itemRequestDto.getQuantity());
//                shoppingItem.setTotalPrice(itemRequestDto.getQuantity() * product.getPrice());
//
//              shoppingItemRepository.save(shoppingItem);
//              return shoppingList;
//
//            }
//            else {
//
//                ShoppingItem shoppingItem = new ShoppingItem();
//                shoppingItem.setProduct(product);
//                shoppingItem.setSalesInfo(shoppingList);
//                shoppingItem.setQuantity(itemRequestDto.getQuantity());
//                shoppingItem.setType(itemRequestDto.getType());
//                shoppingItem.setTotalPrice(itemRequestDto.getQuantity() * product.getPrice());
//                shoppingList.getSaleItemList().add(shoppingItem);
//                productClient.updateStock(product.getId(),itemRequestDto.getQuantity());
//                shoppingItemRepository.save(shoppingItem);
//                //shoppingListRepository.save(shoppingList);
//return shoppingList;
//
//            }

        }







//       ShoppingList shoppingList= shoppingListRepository.findById(shoppingId).orElseThrow(()->new SaleListNotFound("Sale List not found"));
//        Product product = productClient.getProductById(itemRequestDto.getId()).getBody();
//
//        if(shoppingList.getSaleItemList().isEmpty()) {
//            ShoppingItem shoppingItem2 = new ShoppingItem();
//            shoppingItem2.setProduct(product);
//            shoppingItem2.setType(itemRequestDto.getType());
//            shoppingItem2.setQuantity(itemRequestDto.getQuantity());
//            shoppingItem2.setSalesInfo(shoppingList);
//            shoppingItem2.setTotalPrice(shoppingItem2.getTotalPrice() + itemRequestDto.getQuantity() * product.getPrice());
//            shoppingList.getSaleItemList().add(shoppingItem2);
//         //    shoppingListRepository.save(shoppingList);
//            shoppingItemRepository.save(shoppingItem2);
//            return shoppingList;
//        }
//        else {
//            if (shoppingList.getSaleItemList().stream().anyMatch(item -> item.getProduct().getId() == itemRequestDto.getId())) {
//                ShoppingItem shoppingItem = shoppingList.getSaleItemList().stream().filter(item -> item.getProduct().getId() == product.getId()).findFirst().get();
//                productClient.updateStock(itemRequestDto.getId(), itemRequestDto.getQuantity());
//                shoppingItem.setQuantity(shoppingItem.getQuantity() + itemRequestDto.getQuantity());
//                shoppingItem.setTotalPrice(shoppingItem.getTotalPrice() + itemRequestDto.getQuantity() * product.getPrice());
//                shoppingItemRepository.save(shoppingItem);
//                return  shoppingList;
//
//            }
//            else {
//                productClient.updateStock(product.getId(), itemRequestDto.getQuantity());
//
//                ShoppingItem shoppingItem = new ShoppingItem();
//                shoppingItem.setProduct(product);
//                shoppingItem.setSalesInfo(shoppingList);
//                shoppingItem.setQuantity(itemRequestDto.getQuantity());
//                shoppingItem.setType(itemRequestDto.getType());
//                shoppingItem.setTotalPrice(itemRequestDto.getQuantity() * product.getPrice());
//
//                shoppingList.getSaleItemList().add(shoppingItem);
//
//                shoppingItemRepository.save(shoppingItem);
//                return  shoppingList;
//
//            }



    //itemrequestdto kontrolü yapılacak


    //   shoppingList = new ShoppingList();
    // shoppingListRepository.save(shoppingList);




    @Override
    public ShoppingList createSale(String cashierToken) {

        ShoppingList shoppingList=new ShoppingList();
        UserInfo userInfo=authClient.getUserInfo(cashierToken).getBody();


        shoppingList.setUser(userRepository.getUserById(userInfo.getId()));
        shoppingListRepository.save(shoppingList);

        return shoppingList;
    }


}
