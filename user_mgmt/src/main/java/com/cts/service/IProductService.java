package com.cts.service;

import java.io.Serializable;
import java.util.List;

import com.cts.domain.Product;


public interface IProductService extends Serializable{

    public Product addProduct(Product product);
    public List<Product> getProducts();
    public Product getProductById(int id) throws Exception;
    
}
