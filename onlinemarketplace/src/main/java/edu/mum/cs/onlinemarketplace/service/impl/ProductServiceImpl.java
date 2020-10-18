package edu.mum.cs.onlinemarketplace.service.impl;

import edu.mum.cs.onlinemarketplace.domain.Ads;
import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.repository.ProductRepository;
import edu.mum.cs.onlinemarketplace.service.AdsService;
import edu.mum.cs.onlinemarketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdsService adsService;

    @Override
    public List<Product> getAllProducts() {

        return productRepository.getAllEnable();
//        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
         productRepository.deleteById(id);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> products = productRepository.findById(id);
        if(products.isPresent()) return products.get();
        return null;
    }

    @Override
    public List<Product> findProductByUserId(Long id) {
        return productRepository.getProductsBySellerId(id);
    }

    @Override
    public List<Product> getProductBySeller(Long id) {
        return productRepository.getProductsBySellerId(id);
    }

    @Override
    public List<Product> getProductsFromAds() {
        List<Ads> adsList = adsService.getAllAds();
        // Get an ads at random
        if(adsList.size() > 0){
            Random rand = new Random();
            Ads ramdomAds = adsList.get(rand.nextInt(adsList.size()));
            System.out.println(ramdomAds.getUser().getId());
            List<Product> products = getProductBySeller(ramdomAds.getUser().getId());
            return products;
        }
        return null;
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.getProductByName(name);
    }
}
