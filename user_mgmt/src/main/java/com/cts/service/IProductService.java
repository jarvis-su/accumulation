package com.cts.service;

import java.io.Serializable;
import java.util.List;

import com.cts.domain.Product;


public interface IProductService extends Serializable{

    Product addProduct(Product product);
    List<Product> getProducts();
    Product getProductById(int id) throws Exception;
    
}
