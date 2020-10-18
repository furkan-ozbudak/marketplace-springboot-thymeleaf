package edu.mum.cs.onlinemarketplace.service.impl;

import edu.mum.cs.onlinemarketplace.domain.UserOrder;
import edu.mum.cs.onlinemarketplace.repository.OrderRepository;
import edu.mum.cs.onlinemarketplace.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<UserOrder> getOrdersBySellerId(Long id) {
        return orderRepository.findOrderBySellerId(id);
    }

    @Override
    public List<UserOrder> getOrdersByBuyerId(Long id) {
        return orderRepository.findOrderByBuyerId(id);
    }

    @Override
    public UserOrder getOrderById(Long id) {
        return (orderRepository.findById(id)).get();
    }

    @Override
    public UserOrder saveOrder(UserOrder userOrder) {
        System.out.println(userOrder.getStatus());
        if(userOrder.getStatus().equals("shipped")){
            LocalDate date = LocalDate.now();
            userOrder.setDeliveryDate(date.plusDays(5));
        }
        return orderRepository.save(userOrder);
    }

    @Override
    public void removeOrderById(Long id) {
        orderRepository.deleteById(id);
    }


}
