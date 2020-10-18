package edu.mum.cs.onlinemarketplace.controller;

import com.itextpdf.text.DocumentException;
import edu.mum.cs.onlinemarketplace.domain.*;
import edu.mum.cs.onlinemarketplace.service.*;

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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/buyer")
public class BuyerShoppingCartController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    PDFService pdfService;

    @GetMapping("/cart")
    public String shoppingCart(Model model, HttpSession session){

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        CreditCard creditCard = user.getCreditCard();
        Cart cart = cartService.getCartById(user.getCart().getId());
        if(cart == null){
            cart = cartService.newCart();
            user.setCart(cart);
            cart = user.getCart();
        }
        cart.calculateTotalPrice();
        cart = cartService.saveCart(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        model.addAttribute("creditCard", creditCard);

        return "cartNew";
    }

    @LogAnnotation
    @PostMapping("/cart/remove/{id}/{pid}")
    public String removeProduct(@PathVariable("id") Long id,
                                @PathVariable("pid") Long pid, Model model, RedirectAttributes redirect){
        Cart cart = cartService.getCartById(id);
        System.out.println(cart.getProductList());
        Product product = productService.findById(pid);
        List<Product> products = cart.getProductList();
        products.remove(product);
//        List<Product> products = cart.getProductList()
//                                        .stream()
//                                        .filter(pd -> pd.getId() != pid)
//                                        .collect(Collectors.toList());
        System.out.println(products);
        cart.setProductList(products);
        cart.calculateTotalPrice();
        cartService.saveCart(cart);
        redirect.addFlashAttribute("resultRemove", true);
        return "redirect:/buyer/cart";
    }

    @PostMapping("/cart/setorder/{cid}")
    public String setOrder(@PathVariable("cid") Long cid, HttpSession session, RedirectAttributes redirect) throws FileNotFoundException, DocumentException {

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        Cart cart = userService.getUserById(user.getId()).getCart();
        //Create orders by sellers
        createOrders(cart, user);
        //Disable current cart and create a new one
        cart.setActive(false);
        cart = cartService.saveCart(cart);
        System.out.println(cart.getProductList());
        Cart newCart = cartService.newCart();
        //Update buyer cart
        user.setCart(newCart);
        session.setAttribute("cartId", newCart.getId());
        user = userService.saveUser(user);

        redirect.addFlashAttribute("added", true);
        return "redirect:/buyer/orders";
    }

    private void createOrders(Cart cart, User user) throws FileNotFoundException, DocumentException {
        //Divide products by sellers
        System.out.println("CART ID:  ---->" + cart.getId());
        HashMap<Long, List<Product>> mapProducts = new HashMap<>();
        List<Long> seller = cart.getProductList().stream().map(prod -> prod.getSeller().getId()).collect(Collectors.toList());
        Set<Long> sellersId = new HashSet<>(seller);
        sellersId.stream().forEach(id ->{
            List<Product> prodList = new ArrayList<>();
            cart.getProductList().stream()
                    .filter(prod -> prod.getSeller().getId() == id)
                    .forEach(prod -> prodList.add(prod));
            mapProducts.put(id, prodList);
        });
        //Create Orders by product sellers
        for (Map.Entry me : mapProducts.entrySet()) {
            List<Product> pds= (List<Product>) me.getValue();
            UserOrder order = new UserOrder();
            order.setStatus("waiting");
            order.setCart(cart);
            order.setTotal(pds.stream().mapToDouble(p -> p.getPrice()).sum());
            order.setCreateDate(LocalDate.now());
            order.setSeller(userService.getUserById((Long) me.getKey()));
            order.setBuyer(user);
            orderService.saveOrder(order);
        }
    }

}
