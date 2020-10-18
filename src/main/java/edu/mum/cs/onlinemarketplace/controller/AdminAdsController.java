package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.Ads;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.service.AdsService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminAdsController {
    @Autowired
    UserService userService;

    @Autowired
    AdsService adsService;

    @GetMapping("/manage/ads")
    public String manageAds(Model model, HttpSession session){
        List<Ads> ads = adsService.getAllAds();
        model.addAttribute("adsList", ads);
        User user= (User) session.getAttribute("user");
        model.addAttribute("Adminuser",userService.findUserById(user.getId()));
        return "adminManageADs";
    }

    @PostMapping("/manage/ads/search")
    public String searchUser(Model model, @RequestParam("email") String email, RedirectAttributes redirect){
        List<User> users = userService.getUserByEmail(email);
        if(users.size() <= 0)
            redirect.addFlashAttribute("notFound", true);
        else
            redirect.addFlashAttribute("users", users);
        return "redirect:/admin/manage/ads";
    }

    @PostMapping("/ads/add/{id}")
    public String addUserAds(Model model, @PathVariable("id") Long id, RedirectAttributes redirect){
        User user = userService.getUserById(id);
        if(!user.getHasAds()){
            user.setHasAds(true);
            Ads ads = new Ads();
            ads.setUser(user);
            userService.saveUser(user);
            adsService.saveAds(ads);
            redirect.addFlashAttribute("added", true);
        }
        return "redirect:/admin/manage/ads";
    }

    @LogAnnotation
    @PostMapping("/ads/remove/{aid}")
    public String removeUserAds(Model model, @PathVariable("aid") Long aid, RedirectAttributes redirect){
        System.out.println("id=============="+aid);
        Ads ads = adsService.getAdsById(aid);
        System.out.println("id=============="+aid);
        User user = ads.getUser();
        if(user != null){
            user.setHasAds(false);
            userService.saveUser(user);
            adsService.deleteAdsById(aid);
            redirect.addFlashAttribute("removed", true);
        }
        return "redirect:/admin/manage/ads";
    }


}
