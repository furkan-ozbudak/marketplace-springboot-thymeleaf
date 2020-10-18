package edu.mum.cs.onlinemarketplace.controller;

import com.itextpdf.text.DocumentException;
import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.domain.UserOrder;
import edu.mum.cs.onlinemarketplace.email.EmailService;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/seller")
public class SellerManagerOrdersController {

    @Autowired
    OrderService orderService;

    @Autowired
    PDFService pdfService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @GetMapping("/orders")
    public String managerOrders(Model model, HttpSession session){

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        List<UserOrder> orders = orderService.getOrdersBySellerId(user.getId());
        model.addAttribute("orders", orders);
        return "sellerManageOrders";
    }

    @LogAnnotation
    @PostMapping("/order/change/{id}/{status}")
    public String cancelOrder(@PathVariable("id") Long id,
                              @PathVariable("status") String status, Model model, RedirectAttributes redirect) throws FileNotFoundException, DocumentException {
        UserOrder userOrder = orderService.getOrderById(id);
        if(userOrder.getStatus().equals("waiting")){
            userOrder.setStatus(status);
            orderService.saveOrder(userOrder);
            redirect.addFlashAttribute("result", status);
            List<Product> products = userOrder.getCart().getProductList()
                    .stream()
                    .filter(prod -> prod.getSeller().getId() == userOrder.getSeller().getId())
                    .collect(Collectors.toList());
            pdfService.createPDFFile(userOrder, products);
//            notifyBuyer(userOrder.getBuyer());



        }
        //Update Buyer points
        if(userOrder.getStatus().equals("shipped")){
            Integer points = (int)(userOrder.getTotal()/100);
            if(points <= 0) points = 1;
            User user = userOrder.getBuyer();
            user.setPoints(user.getPoints() + points);
            userService.saveUser(user);
        }
        return "redirect:/seller/orders";
    }


    public void notifyBuyer(User buyer){

        String messageBody = "Dear" +buyer.getName() + "You Order Has Been Shipped Successfully";
        String subject = "MUM Express, Order Shipped";
        emailService.sendSimpleMessage(buyer.getEmail(),subject,messageBody);


    }


}
