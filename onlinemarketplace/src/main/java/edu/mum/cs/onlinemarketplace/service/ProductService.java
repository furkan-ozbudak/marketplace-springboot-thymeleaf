package edu.mum.cs.onlinemarketplace.service;


import edu.mum.cs.onlinemarketplace.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product>getAllProducts();
    Product save(Product product);
    void delete(Long id);
    Product findById(Long id);
    List<Product>findProductByUserId(Long id);
    List<Product>getProductBySeller(Long id);
    List<Product>getProductsFromAds();
    List<Product>getProductByName(String name);


}
