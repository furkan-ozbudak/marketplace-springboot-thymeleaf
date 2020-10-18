package edu.mum.cs.onlinemarketplace.repository;
import edu.mum.cs.onlinemarketplace.domain.Address;
import edu.mum.cs.onlinemarketplace.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User>findFollowersById(Long sid);

    @Query("select u from User u where u.name like %:name%")
    List<User> findUserByName(String name);

    @Query("SELECT u FROM User u where u.email like %:email%")
    List<User> findByEmail(String email);

    Long countByEmail(String email);

}
