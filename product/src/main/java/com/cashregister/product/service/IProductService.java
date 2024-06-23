package com.cashregister.product.service;


import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;

import java.util.List;

public interface IProductService {


    void saveProduct(ProductWithCategoryDto product);
    void updateStock(long productId, int stock);

    void deleteProduct(long id);
    List<Product> getAllProduct();
    Product getProductById(long id);

}
