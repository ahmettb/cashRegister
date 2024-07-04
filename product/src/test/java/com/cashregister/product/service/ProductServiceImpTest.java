package com.cashregister.product.service;

import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;
import com.cashregister.product.repository.IProductRepository;
import org.junit.jupiter.api.AfterEach;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mapping.Association;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImpTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductServiceImp productService;
    private Product product;


    @BeforeEach
    void setUp() {

        product = new Product();
        product.setId(2L);
        product.setPrice(30.99);
        product.setDeleted(false);
        product.setStockCount(200);
        product.setName("Biber");


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveProduct() {

        ProductWithCategoryDto productWithCategoryDto =
                ProductWithCategoryDto.builder().name(product.getName()).categoryId(product.getId()).
                        price(product.getPrice()).stock(product.getStockCount()).description(product.getDescription()).build();

        productService.saveProduct(productWithCategoryDto);
      //  verify(productRepository,times(1)).save(productWithCategoryDto);

    }

    @Test
    void updateStock() {
    }

    @Test
    void deleteProduct() {

        long productId = 1L;
        willDoNothing().given(productRepository).deleteById(productId);

        //productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);


    }

    @Test
    void getAllProduct() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setPrice(25.0);
        product1.setDeleted(false);
        product1.setStockCount(150);
        product1.setName("Domates");


        given(productRepository.findAll()).willReturn(List.of(product, product1));
        //List<Product> productList = productService.getAllProduct();


        //assertThat(productList).isNotNull();
        //assertThat(productList.size()).isEqualTo(2);


    }

    @Test
    void getProductById() {

        Product product = new Product();
        product.setId(1L);
        product.setPrice(25.0);
        product.setDeleted(false);
        product.setStockCount(150);
        product.setName("Domates");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

       // Product foundProduct = productService.getProductById(1L);

       // assertNotNull(foundProduct);
        assertEquals("Domates", product.getName());


    }

    @Test
    void testSaveProduct() {
    }

    @Test
    void testUpdateStock() {
    }

    @Test
    void testDeleteProduct() {
    }

    @Test
    void testGetAllProduct() {
    }

    @Test
    void testGetProductById() {
    }
}