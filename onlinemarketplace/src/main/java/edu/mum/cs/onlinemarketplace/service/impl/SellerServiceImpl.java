package edu.mum.cs.onlinemarketplace.service.impl;

import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.repository.SellerRepository;
import edu.mum.cs.onlinemarketplace.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public List<User> getAllSeller() {
        return sellerRepository.getAllSeller();
    }

    @Override
    public User save(User user) {
        return sellerRepository.save(user);
    }

    @Override
    public User delete(Long id) {
         return sellerRepository.deleteSellerById(id);
    }

    @Override
    public User findUserBySellerId(Long id) {
        return sellerRepository.getUserBySellerId(id);
    }

    @Override
    public List<User> getAllPendingSeller() {
        return sellerRepository.getAllPendingSeller();
    }
}
