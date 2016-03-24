package com.cts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.domain.Product;
import com.cts.service.IProductService;

@Service
public class SimpleProductServiceImpl implements IProductService {

    private static final long serialVersionUID = 1L;

    @Override
    public Product addProduct(Product product) {
        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();
        
        Product p1 = new Product();
        p1.setPrice(87.0);
        p1.setDescription("Thinking in Java");
        products.add(p1);
        
        return products;
    }

    @Override
    public Product getProductById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

}
