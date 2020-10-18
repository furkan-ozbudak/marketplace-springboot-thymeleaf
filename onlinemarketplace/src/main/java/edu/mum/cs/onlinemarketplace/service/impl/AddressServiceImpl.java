package edu.mum.cs.onlinemarketplace.service.impl;


import edu.mum.cs.onlinemarketplace.domain.Address;
import edu.mum.cs.onlinemarketplace.repository.AddressRepository;
import edu.mum.cs.onlinemarketplace.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {


    @Autowired
    AddressRepository addressRepository;

//    @Override
//    public List<Address> getAddressByUserId(Long id) {
//        return addressRepository.getAddressByUserID(id);
//    }


}
