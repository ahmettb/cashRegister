package com.cashregister.product.service;

import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;
import com.cashregister.product.repository.ICategoryRepository;
import com.cashregister.product.repository.IProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImp implements IProductService {


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
        productRepository.save(product);
    }

    @Override
    public void deleteProduct() {

    }

    @Override
    public List<Product> getAllProduct() {


        return  productRepository.findAll();


    }

    @Override
    public Product getProductVById(long id) {

       return productRepository.findById(id).orElseThrow(() -> new RuntimeException("No item by id"));

    }


}
