package com.cashregister.product.service;

import com.cashregister.product.exception.CategoryNotFound;
import com.cashregister.product.exception.ProductNotFound;
import com.cashregister.product.exception.StockNotEnough;
import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;
import com.cashregister.product.repository.ICategoryRepository;
import com.cashregister.product.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ProductServiceImp implements IProductService {

    final IProductRepository productRepository;
    final ICategoryRepository categoryRepository;

    @Override
    public void saveProduct(ProductWithCategoryDto productDto) {
        log.info("ProductServiceImp: saveProduct method called with productDto = {}", productDto);

        try {
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() ->
                    new CategoryNotFound("Invalid category id")
            );
            Product product = new Product();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setCategory(category);
            product.setStockCount(productDto.getStock());
            productRepository.save(product);
            log.info("ProductServiceImp: Product added successfully with productDto = {}", productDto);
        } catch (Exception e) {
            log.error("ProductServiceImp: Error in saveProduct method with productDto = {}. Error: {}", productDto, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateStock(long productId, int stock) {
        log.info("ProductServiceImp: updateStock method called for productId = {} with stock = {}", productId, stock);

        try {
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new ProductNotFound("Product Not Found")
            );
            log.info("ProductServiceImp: Old stock for productId {} is {}", productId, product.getStockCount());
            if (product.getStockCount() < stock) {
                throw new StockNotEnough("Stock Not Enough");
            }
            product.setStockCount(product.getStockCount() - stock);
            log.info("ProductServiceImp: New stock for productId {} is {}", productId, product.getStockCount());
            log.info("ProductServiceImp: updateStock method completed successfully for productId = {}", productId);
        } catch (Exception e) {
            log.error("ProductServiceImp: Error in updateStock method for productId = {}. Error: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteProduct(long id) {
        log.info("ProductServiceImp: deleteProduct method called for productId = {}", id);

        try {
            Product product = productRepository.findById(id).orElseThrow(() ->
                    new ProductNotFound("Product Not Found")
            );
            product.setDeleted(true);
            productRepository.save(product);
            log.info("ProductServiceImp: deleteProduct method completed successfully for productId = {}", id);
        } catch (Exception e) {
            log.error("ProductServiceImp: Error in deleteProduct method for productId = {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> getAllProduct() {
        log.info("ProductServiceImp: getAllProduct method called");
        List<Product> products = productRepository.findAll();
        log.info("ProductServiceImp: getAllProduct method returned {} products", products.size());
        return products;
    }

    @Override
    public Product getProductById(long id) {
        log.info("ProductServiceImp: getProductById method called for productId = {}", id);

        try {
            Product product = productRepository.findById(id).orElseThrow(() ->
                    new ProductNotFound("Product Not Found")
            );
            log.info("ProductServiceImp: getProductById method completed successfully for productId = {}", id);
            return product;
        } catch (Exception e) {
            log.error("ProductServiceImp: Error in getProductById method for productId = {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }
}
