package com.cts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.domain.Product;
import com.cts.exception.BusinessException;
import com.cts.exception.ParameterException;
import com.cts.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    private static final long serialVersionUID = 1L;

    @Override
    public Product addProduct(Product product) {
        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();

        Product p1 = new Product();
        p1.setPrice(22.0);
        p1.setDescription("football");
        products.add(p1);

        Product p2 = new Product();
        p2.setPrice(33.3);
        p2.setDescription("JavaScript: The Good Parts");
        products.add(p2);

        return products;
    }

    @Override
    public Product getProductById(int id) throws Exception {
        switch (id) {
        case 1:
            throw new ParameterException("invalid product id");
        case 2: 
            throw new BusinessException("permission deny");
        default:
            return new Product("Java", 18d);
        }
    }

}
