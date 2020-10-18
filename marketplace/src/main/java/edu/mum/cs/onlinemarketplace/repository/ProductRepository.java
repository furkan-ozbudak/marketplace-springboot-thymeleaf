package edu.mum.cs.onlinemarketplace.repository;

import edu.mum.cs.onlinemarketplace.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where p.seller.id=:id and p.enable=true")
    public List<Product>getProductsBySellerId(Long id);

    @Query("SELECT p FROM Product p WHERE p.seller.id= :id and p.enable=true")
    public List<Product>geBySeller(Long id);

    @Query("select p from Product p where p.enable=true")
    public List<Product>getAllEnable();

//    @Query("select p from Product p where p.id=:id and p.enable=true")
//    public Optional<Product> getEnableById(Long id);

//    @Query("delete from Product p where p.id=:id")
//    public void deleteById(Long id);

    @Query("select p from Product p where lower(p.name) like %:name%")
    public List<Product>getProductByName(String name);
}
