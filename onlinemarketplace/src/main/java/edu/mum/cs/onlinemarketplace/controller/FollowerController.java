package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.service.SellerService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FollowerController {
    @Autowired
    private UserService userService;
    @Autowired
    private SellerService sellerService;

    @GetMapping("/users/followerList")
    public String getFollowerList(Model model, HttpSession session){

        //Get seller id from session
        Long id = ((User) session.getAttribute("user")).getId();
        List<User> userList = userService.getUserById(id).getUserList();
        model.addAttribute("followerList",userList);

        return "followerList";
    }

    @PostMapping("/users/follow/{uid}")
    public String addFollower(@PathVariable("uid")Long uid, HttpSession session){


        Long id = ((User) session.getAttribute("user")).getId();
        User me = userService.getUserById(id);
        User followerUser = userService.findUserById(uid);
        System.out.println(me.getUserList());


        List<User> userlist = me.getUserList();
        userlist.add(followerUser);

        List<User> followlist = followerUser.getUserList();
        followlist.add(me);

        userService.saveUser(me);
        userService.saveUser(followerUser);
        System.out.println(me.getUserList());

        return "redirect:/users/followerList";
    }
    @LogAnnotation
    @PostMapping("/users/unfollow/{uid}")
    public String removeFollower(@PathVariable("uid")Long uid, HttpSession session){

        Long id = ((User) session.getAttribute("user")).getId();
        User me = userService.getUserById(id);

        User followerUser = userService.findUserById(uid);

        List<User> userlist = me.getUserList();
        userlist.remove(followerUser);

        List<User> followlist = followerUser.getUserList();
        followlist.remove(me);

        userService.saveUser(me);
        userService.saveUser(followerUser);

        return "redirect:/users/followerList";
    }

}
