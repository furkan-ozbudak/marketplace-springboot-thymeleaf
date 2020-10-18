package edu.mum.cs.onlinemarketplace.service;

import edu.mum.cs.onlinemarketplace.domain.User;

import java.util.List;

public interface SellerService {
    List<User>getAllSeller();
    User save(User user);
    User delete(Long id);
    User findUserBySellerId(Long id);
    List<User>getAllPendingSeller();
}
