package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.Role;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.service.CartService;
import edu.mum.cs.onlinemarketplace.service.RoleService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RoleService roleService;

    @ModelAttribute("types")
    public List<Role> getRoles(Model model){
        return roleService.findAllCommonRoles();
    }

    @RequestMapping(value = {"/register_input" })
    public String registerInput(@ModelAttribute("user") User user, HttpSession session) {
        User us = (User)session.getAttribute("user");
        if(us == null) return "registerFormNew";
        return "redirect:/";
    }

    @RequestMapping(value = "/register_save")
    public String registerSave(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                               Model model, RedirectAttributes ra) {

        if(userService.countByEmail(user.getEmail()) > 0){
            return "registerFormNew";
        }
        if (bindingResult.hasErrors()) return "registerFormNew";

//        String[] suppressedFields = bindingResult.getSuppressedFields();
//        if (suppressedFields.length > 0) {
//            throw new RuntimeException("Attempt to bind fields that haven't been allowed in initBinder(): "
//                    + StringUtils.addStringToArray(suppressedFields, ", "));
//        }

        user.setCreateDate(LocalDate.now());
        user.setPoints(0);
        user.setActive(true);
        user.setHasAds(false);
        user.setCart(cartService.newCart());
        userService.saveUser(user);

        model.addAttribute("user", user);
        ra.addFlashAttribute("confirmationMessage", "You are successfully registered " + user.getName() + ".");
        return "redirect:/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String getLogin(HttpSession session, Model model) {
        return "login";
    }
}
