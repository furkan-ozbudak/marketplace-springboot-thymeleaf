package edu.mum.cs.onlinemarketplace.service;

import edu.mum.cs.onlinemarketplace.domain.UserOrder;

import java.util.List;

public interface OrderService {

    List<UserOrder> getOrdersBySellerId(Long id);
    List<UserOrder> getOrdersByBuyerId(Long id);
    UserOrder getOrderById(Long id);
    UserOrder saveOrder(UserOrder userOrder);
    void removeOrderById(Long id);
}
