package edu.mum.cs.onlinemarketplace.service;



import edu.mum.cs.onlinemarketplace.domain.Address;

import edu.mum.cs.onlinemarketplace.domain.User;

import java.util.List;

public interface UserService {

    public User getUserById(Long id);

    User findUserById(Long id);

    User saveUser(User user);

    List<User>findAllFollowers(Long sid);


    List<User>findUserByName(String name);


    List<User> getUserByEmail(String email);

    Long countByEmail(String email);

}
