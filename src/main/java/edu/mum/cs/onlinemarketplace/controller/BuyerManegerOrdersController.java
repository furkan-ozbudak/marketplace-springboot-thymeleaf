package edu.mum.cs.onlinemarketplace.controller;

import com.itextpdf.text.*;
import edu.mum.cs.onlinemarketplace.domain.CreditCard;
import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.domain.UserOrder;
import edu.mum.cs.onlinemarketplace.service.OrderService;
import edu.mum.cs.onlinemarketplace.service.PDFService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/buyer")
public class BuyerManegerOrdersController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    PDFService pdfService;

    @GetMapping("/orders")
    public String userOrders(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        CreditCard creditCard = user.getCreditCard();
        List<UserOrder> orders = orderService.getOrdersByBuyerId(user.getId());
        Comparator<UserOrder> compareByStatus = (UserOrder u1, UserOrder u2) -> u1.getStatus().compareTo( u2.getStatus() );
        Collections.sort(orders, compareByStatus.reversed());
        System.out.println(orders);
        model.addAttribute("user", user);
        model.addAttribute("creditCard", creditCard);
        model.addAttribute("orders", orders);
        return "buyerManageOrders";
    }

    @LogAnnotation
    @PostMapping("/order/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) throws FileNotFoundException, DocumentException {
        UserOrder order = orderService.getOrderById(id);
        if(order.getStatus().equals("waiting")){
            order.setStatus("canceled");
            orderService.saveOrder(order);
            redirect.addFlashAttribute("result", true);

            List<Product> products = order.getCart().getProductList()
                    .stream()
                    .filter(prod -> prod.getSeller().getId() == order.getSeller().getId())
                    .collect(Collectors.toList());
            pdfService.createPDFFile(order, products);
        }
        return "redirect:/buyer/orders";
    }

}
