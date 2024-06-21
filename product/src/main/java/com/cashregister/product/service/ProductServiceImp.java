package com.cashregister.product.service;

import com.cashregister.product.controller.ProductController;
import com.cashregister.product.exception.GlobalExceptionHandler;
import com.cashregister.product.exception.ProductNotFound;
import com.cashregister.product.exception.StockNotEnough;
import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;
import com.cashregister.product.repository.ICategoryRepository;
import com.cashregister.product.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class ProductServiceImp implements IProductService {

    private static final Logger logger= LogManager.getLogger(ProductServiceImp.class);

    final IProductRepository productRepository;
    final ICategoryRepository categoryRepository;

    @Override
    public void saveProduct(ProductWithCategoryDto productDto) {

       Category category= categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() ->

new IllegalArgumentException("Invalid category ıd")
                );
        Product product=new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setStockCount(productDto.getStock());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateStock(long productId, int stock) {

        try {
           Product product= productRepository.findById(productId).get();
            log.info("Old stock {}",product.getStockCount());
            log.info("request stock {}",stock);


            if(product.getStockCount()<stock)
           {
               throw new StockNotEnough("STOCK NOT ENOUGH");
           }
           product.setStockCount(product.getStockCount()-stock);

            log.info("New stock {}",product.getStockCount());



        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());

        }

    }

    @Override
    public void deleteProduct(long id) {
        try {
             Product product= productRepository.findById(id).get();
             product.setDeleted(true);
             productRepository.save(product);
             logger.info("Product deleted by id {} : ",id);
        }catch (Exception e){

            throw new ProductNotFound("Product not found");
        }


    }



    @Override
    public List<Product> getAllProduct() {



        return  productRepository.findAll();


    }

    @Override
    public Product getProductVById(long id) {


          Product product= productRepository.findById(id).orElseThrow(()->  new ProductNotFound("PRODUCT NOT FOUND"));

            return  product;





    }


}
