package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.Review;
import edu.mum.cs.onlinemarketplace.domain.Role;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.email.EmailService;
import edu.mum.cs.onlinemarketplace.service.ReviewService;
import edu.mum.cs.onlinemarketplace.service.RoleService;
import edu.mum.cs.onlinemarketplace.service.SellerService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


//    @ModelAttribute("Adminuser")
//    public User getUserInSession( HttpSession session){
//        User user= (User) session.getAttribute("user");
//        return userService.findUserById(user.getId());
//    }


    @ModelAttribute("types")
    public List<Role> getRoles(Model model){
        return roleService.getAllRoles();
    }

    @GetMapping("/users/SellerList")
    public String getAllSeller(Model model, HttpSession session){
        model.addAttribute("sellerList",sellerService.getAllSeller());
        System.out.println("Seller="+sellerService.getAllSeller());
       User user= (User) session.getAttribute("user");
       model.addAttribute("Adminuser",userService.findUserById(user.getId()));
        System.out.println("session inside Admin==="+session.getAttribute("user"));
        return "adminHome";
    }

    @GetMapping("/users/manageSellers")
    public String manageSellerForm(Model model,HttpSession session){
        model.addAttribute("manageSeller",sellerService.getAllPendingSeller());
//        System.out.println("Seller="+sellerService.getAllPendingSeller());
        User user= (User) session.getAttribute("user");
        model.addAttribute("Adminuser",userService.findUserById(user.getId()));

        return "manageSeller";
    }

    @PostMapping("/users/addSeller/{id}")
    public String approveSeller(@PathVariable("id")Long id,Model model){
        User newSeller = sellerService.findUserBySellerId(id);
        newSeller.setName(newSeller.getName());
        newSeller.setName(newSeller.getEmail());
        newSeller.setPassword(newSeller.getPassword());
        newSeller.setStatus("Approved");
//        System.out.println("status==============="+status);
        sellerService.save(newSeller);
        emailService.sendSimpleMessage(newSeller.getEmail(),"Accepted","Congratulations!! You are accepted as Seller.");
        return "redirect:/admin/users/manageSellers";
    }

    @LogAnnotation
    @PostMapping("/users/removeSeller/{id}")
    public String removeSeller(@PathVariable("id")Long id){
        User newSeller = sellerService.findUserBySellerId(id);
        newSeller.setName(newSeller.getName());
        newSeller.setName(newSeller.getEmail());
        newSeller.setPassword(newSeller.getPassword());
        Role role = roleService.getRoleById(3L);
        newSeller.setType(role);
//        newSeller.setType("BUYER");
//        System.out.println("status==============="+status);
        sellerService.save(newSeller);
        emailService.sendSimpleMessage(newSeller.getEmail(),"Rejected","Sorry! we can't approve you as Seller");
        return "redirect:/admin/users/manageSellers";
    }

    @GetMapping("/users/manageReviews")
    public String manageReviewForm(Model model,HttpSession session){
        model.addAttribute("reviewList",reviewService.getAllReview());
        User user= (User) session.getAttribute("user");
        model.addAttribute("Adminuser",userService.findUserById(user.getId()));
        return "manageReview";
    }
    @PostMapping("/users/manageReview/{rid}/accept")
    public String acceptReview(@PathVariable("rid") Long rid,Model model){
        Review updateReview = reviewService.findReviewById(rid);
        updateReview.setStatus("approved");
        reviewService.save(updateReview);
        User u = updateReview.getUser();
        reviewNotify(u);
        return "redirect:/admin/users/manageReviews";
    }
    @PostMapping("/users/manageReview/{rid}/delete")
    public String deleteReview(@PathVariable("rid") Long rid){
        reviewService.delete(rid);
        return "redirect:/admin/users/manageReviews";
    }

    public void reviewNotify(User user){

        String messageBody = "Hello " +user.getName()+ " Your review Has been Approved.";
        String subject = "Mum Express, Review Approved";
        emailService.sendSimpleMessage(user.getEmail(),subject,messageBody);
        System.out.println("Email::::"+user.getEmail());



    }



}
