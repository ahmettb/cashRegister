package com.cashregister.product.service;


import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;

import java.util.List;

public interface IProductService {


    void saveProduct(ProductWithCategoryDto product);

    void deleteProduct();
    List<Product> getAllProduct();
    Product getProductVById(long id);

}
