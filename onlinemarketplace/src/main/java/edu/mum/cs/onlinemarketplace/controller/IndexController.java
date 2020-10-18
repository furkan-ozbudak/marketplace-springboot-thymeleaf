package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.service.AdsService;
import edu.mum.cs.onlinemarketplace.service.ProductService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("user")
public class IndexController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    AdsService adsService;

    @RequestMapping(value="/")
    public String getAllProducts(Model model, HttpSession session, Authentication authentication){

        User user = new User();
        model.addAttribute("allProducts",productService.getAllProducts());
        //Get Ads
        List<Product> products = productService.getProductsFromAds();
        if(products != null) model.addAttribute("adsProducts", products);

        if(authentication != null) {
            if(!model.containsAttribute("user")){
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                List<User> userList = userService.getUserByEmail(userDetails.getUsername());
                user = userList.get(0);
                model.addAttribute("user", user);
            } else {
                user = ((User)session.getAttribute("user"));
            }
            session.setAttribute("type", user.getType().getName());
        } else {
            session.setAttribute("type", "OFF");
        }
        return "index";
    }

    @GetMapping("/search/products")
    public String searchProducts(@RequestParam("search")String search, Model model){
        System.out.println("value ======"+search);
       model.addAttribute("allProducts",productService.getProductByName(search));
        System.out.println("products============"+productService.getProductByName(search));
       return "index";
    }
}
