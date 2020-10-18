package edu.mum.cs.onlinemarketplace.repository;

import edu.mum.cs.onlinemarketplace.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.type=2 and u.status='Approved'")
    public List<User>getAllSeller();

    @Query("select u from User u where u.type =2 and u.id=:id")
    public User getUserBySellerId(Long id);

    @Query("delete from User u where u.type=2 and u.id=:id")
    public User deleteSellerById(Long id);


    @Query("select  s from User s where s.type=2 and s.status is null")
    public List<User>getAllPendingSeller();
}
