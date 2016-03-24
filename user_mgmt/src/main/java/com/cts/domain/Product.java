package com.cts.domain;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Product implements Serializable{

    private static final long serialVersionUID = 1L;
    
    public Product() {
        super();
    }

    public Product(String description, Double price) {
        super();
        this.description = description;
        this.price = price;
    }

    @Size(min=2, max=20)
    private String description;
    @NotNull @Min(2) @Max(1000)
    private Double price;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Description: " + description + ";");
        buffer.append("Price: " + price);
        return buffer.toString();
    }

}
