package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.Cart;
import edu.mum.cs.onlinemarketplace.domain.Product;
import edu.mum.cs.onlinemarketplace.domain.Review;
import edu.mum.cs.onlinemarketplace.domain.User;
import edu.mum.cs.onlinemarketplace.service.CartService;
import edu.mum.cs.onlinemarketplace.service.ProductService;
import edu.mum.cs.onlinemarketplace.service.ReviewService;
import edu.mum.cs.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SessionAttributes({"username","userId"})
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CartService cartService;

    @GetMapping("/products")
    public String getAllProducts(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        List<Product> products = productService.findProductByUserId(user.getId());
        if(products.size() <= 0)
            model.addAttribute("message","This is unavailable");
        else
            model.addAttribute("productList", products);

        return "home";
    }

    @GetMapping(value = "/addProduct")
    public String getProductForm(@ModelAttribute("newProduct") Product product, Model model, HttpSession session){

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        model.addAttribute("userId",userService.findUserById(user.getId()));
//        if(!model.containsAttribute("userId")){
//            model.addAttribute("message","This is currently unavailable");
//            return "errorMsg";
//        }
        model.addAttribute("product",new Product());

        return "addProductFormNew";
    }

    @RequestMapping(value = "/product/add/new")
    public String addProduct(HttpSession session, @Valid @ModelAttribute("newProduct") Product product, BindingResult result, Model model,@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {

        Long id = ((User) session.getAttribute("user")).getId();
        User user = userService.getUserById(id);
        product.setCreateDate(LocalDate.now());
        product.setSeller(user);
        product.setEnable(true);
        Product newProduct= productService.save(product);

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "addProductFormNew";
        }else {
            checkfile(file, newProduct);
            productService.save(newProduct);
            return "redirect:/products";
        }
    }

    @LogAnnotation
    @PostMapping("/product/delete/{pid}")
    public String deleteProduct(@PathVariable Long pid){
        Product product = productService.findById(pid);
        product.setEnable(false);
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/product/update/{pid}")
    public String updateProductForm(@PathVariable(value = "pid",required = false) Long pid, @ModelAttribute("updateProduct") Product product,Model model){
        model.addAttribute("product",productService.findById(pid));
        return "updateProductForm";
    }

    @PostMapping("/product/update/{pid}")

    public String updateProduct(Product product, @PathVariable Long pid,@RequestParam(value = "file",required = false) MultipartFile file, HttpSession session) throws IOException {
        Product updateProduct = productService.findById(pid);
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        checkfile(file, updateProduct);
       // updateProduct.setProductImage(product.getProductImage());
        productService.save(updateProduct);


//        User sellerObject = updateProduct.getSeller();
//        List<User> followersList = sellerObject.getUserList();
//        session.setAttribute("notifyUsers", followersList);
//        session.setAttribute("product", updateProduct);



        return "redirect:/products";
    }



    @GetMapping("/product/{pid}")
    public String viewProduct(@ModelAttribute("newReview") Review review, @PathVariable("pid")Long id, Model model, HttpSession session){

        Product product = productService.findById(id);
        model.addAttribute("product",product);
        model.addAttribute("reviews", reviewService.getReviewsByProduct(id));

        Long sellerId = product.getSeller().getId();
        model.addAttribute("productByseller",productService.getProductBySeller(sellerId));

        Long uid = ((User) session.getAttribute("user")).getId();
        User user = userService.getUserById(uid);

        System.out.println("Session inside product is ="+user);
        //Set user data
        if(user == null){
//            System.out.println(" inside if Session inside product is ="+user1.getType());
        }
        else {

            if (user.getType().getName().equalsIgnoreCase("BUYER")) {
                System.out.println(" inside else Session inside product is ="+user.getUserList().toString());

                List<User> follow = user.getUserList();
                System.out.println("followers==="+follow);
//                List<User> followList = follow.stream().filter(u -> u.getId() == product.getSeller().getId()).collect(Collectors.toList());
//                followList.contains(product.getSeller());
                if (follow.contains(product.getSeller())) {
                    model.addAttribute("follow", 1);
                } else {
                    model.addAttribute("follow", 0);
                }
            }
            session.setAttribute("user", user);
            model.addAttribute("product", product);
            model.addAttribute("reviews", reviewService.getReviewsByProduct(id));
        }
        return "single";
    }

    @PostMapping("/product/{pid}/addToCart")
    public String addToCart(@PathVariable("pid") Long pid, Model model, HttpSession session, RedirectAttributes redirect){

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        Cart cart = cartService.getCartById(user.getCart().getId());
        Product product = productService.findById(pid);
        cart.getProductList().add(product);
        cart = cartService.saveCart(cart);
        redirect.addFlashAttribute("added", true);
        return "redirect:/buyer/cart";
    }


    @PostMapping("/product/{pid}/newReview")
    public String addReview(@Valid @ModelAttribute("newReview") Review review, BindingResult result, @PathVariable Long pid,
                            Model model, HttpSession session){

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        review.setCreateDate(LocalDate.now());
        review.setProduct(productService.findById(pid));
        review.setUser(userService.findUserById(user.getId()));

        if(result.hasErrors())
            return "/product/{pid}";
        else {
            reviewService.addReview(review);
            return "redirect:/product/{pid}";
        }
    }

    public void checkfile(MultipartFile file, Product product) throws IOException {
        if (!file.isEmpty()) {
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            File dir = new File("src/main/resources/static/imgages/");
            dir.mkdir();
            File destination = new File(dir, product.getName() + product.getId() + ".jpg");
            destination.createNewFile();
            ImageIO.write(src, "JPG", destination);
            product.setProductImage(destination.getName());
        }
    }





}
