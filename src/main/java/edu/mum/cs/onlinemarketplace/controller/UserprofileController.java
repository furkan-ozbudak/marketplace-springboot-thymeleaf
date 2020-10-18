package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.Address;
import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.repository.UserRepository;
import edu.mum.cs.onlinemarketplace.service.AddressService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserprofileController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping("/profile/{id}")
    public String getUserProfile(@PathVariable(value = "id", required = false) Long id, Model model, HttpSession session){

        User sessionuser = (User) session.getAttribute("user");
        User user = userService.findUserById(id);
        if(user == null) return "redirect:/";
        model.addAttribute("user", user);
        model.addAttribute("sessionUser",sessionuser);
        model.addAttribute("shippingAddress", user.getShippingAddress());
        model.addAttribute("billingAddress", user.getBillingAddress());
//        model.addAttribute("addresses", addressService.getAddressByUserId(id));
        return "profile";
    }

    @GetMapping("/profile/{id}/edit")
    public String getEditProfile(@ModelAttribute("user") User user,@PathVariable  Long id, Model model, HttpSession session){
        User objUser = (User)session.getAttribute("user");
        if(user == null) return "redirect:/";
        model.addAttribute("user", objUser);
        return "editProfileNew";
    }

    @PostMapping("/profile/{id}/update")
    public String updateProfile(@Valid @ModelAttribute("user")User user, BindingResult result, @PathVariable Long id,
                                Model model, HttpSession session){
        if (result.hasErrors()) {
            return "editProfileNew";
        }
        User u = (User)session.getAttribute("user");
        if(user == null) return "redirect:/";
        u.setName(user.getName());
        u.setCreditCard(user.getCreditCard());
        u.setShippingAddress(user.getShippingAddress());
        u.setBillingAddress(user.getBillingAddress());
        userService.saveUser(u);
        return "redirect:/profile/{id}";
    }


//    @RequestMapping(value = "/notification")
//    public String notification(HttpSession session, Model model){
//
//      List<User> users = session.getAttribute(notifyUsers);
//      Product p = session.getAttribute(product);
//      model.addAttribute("newProduct",p);
//      model.addAttribute("userLists",users);
//      return "notification";
//    }







}
