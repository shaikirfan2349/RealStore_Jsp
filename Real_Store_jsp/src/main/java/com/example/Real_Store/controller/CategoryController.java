package com.example.Real_Store.controller;

import com.example.Real_Store.dto.AdminDTO;
import com.example.Real_Store.dto.CategoryDTO;
import com.example.Real_Store.dto.ProductDTO;
import com.example.Real_Store.dto.CustomerDTO;
import com.example.Real_Store.entity.Category;
import com.example.Real_Store.entity.Product;
import com.example.Real_Store.service.CategoryService;
import com.example.Real_Store.service.ProductService;
import com.example.Real_Store.service.UserService;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CategoryController {

    @Autowired
    public UserService userService;
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public ProductService productService;
    List<Long> viewFinalList = new ArrayList<>();
    List<Product> viewFinalProductList = new ArrayList<>();
    List<Product> selectedProductList = new ArrayList<>();

    @RequestMapping("/")
    public String start(){
        return "start";
    }

    @RequestMapping("/regUser")
    public String regUser(Model model){
        CustomerDTO customerDTO = new CustomerDTO();
        model.addAttribute("user", customerDTO);
        return "registrationForm";
    }

    @RequestMapping("/registerUser")
    public String registerUser(@ModelAttribute ("user") CustomerDTO customerDTO){
        userService.registerUser(customerDTO);
        return "redirect:/userLogins";
    }

    @RequestMapping("regAdmin")
    public String regAdmin(Model model){
        AdminDTO adminDTO = new AdminDTO();
        model.addAttribute("admin",adminDTO);
        return "adminRegistrationForm";
    }

    @RequestMapping("registerAdmin")
    public String registerAdmin(@ModelAttribute("admin") AdminDTO adminDTO){
        userService.registerAdmin(adminDTO);
        return "redirect:/adminLogins";
    }

    @RequestMapping("/userLogins")
    public String userLogin(Model model) {
        CustomerDTO customerDTO = new CustomerDTO();
        model.addAttribute("user", customerDTO);
        return "userLoginForm";
    }

    @RequestMapping("/adminLogins")
    public String adminLogin(Model model) {
        AdminDTO adminDTO = new AdminDTO();
        model.addAttribute("admin",adminDTO);
        return "adminLogin";
    }

    @RequestMapping("verifyUserCredentials")
    public String verifyUserCredential(@RequestParam String userName, @RequestParam String password,Model model){
        CustomerDTO customerDTO = userService.verifyUserCredential(userName,password);
        if(Objects.nonNull(customerDTO)){
            model.addAttribute("user", customerDTO);
            model.addAttribute("userId", customerDTO.getUserId());

            return "redirect:/userDashboard?userId="+ customerDTO.getUserId();
        }
        return "invalidCredentials";
    }

    @RequestMapping("verifyAdminCredentials")
    public String verifyAdminCredential(@RequestParam String adminName, @RequestParam String password,Model model){
        AdminDTO adminDTO = userService.verifyAdminCredential(adminName,password);
        if(Objects.nonNull(adminDTO)){
            model.addAttribute("admin",adminDTO);
            model.addAttribute("adminId",adminDTO.getAdminId());

            return "redirect:/adminDashboard?adminId="+adminDTO.getAdminId();
        }
        return "invalidCredentials";
    }

    @RequestMapping("userDashboard")
    public String userDashboard(@RequestParam("userId")Long userId, Model model){
        CustomerDTO userDTO = userService.getUserById(userId);
        if(Objects.nonNull(userDTO)){
            List<Category> categoryList = categoryService.getAllCategory();
            List<Product> productList = productService.getAllProduct();
            model.addAttribute("categoryList",categoryList);
            model.addAttribute("productList",productList);
            model.addAttribute("user",userDTO);
        }
        return "userDashboard";
    }

    @RequestMapping("submitSelectedProducts")
    public String submitSelectedProducts(@RequestParam("productIds") List<Long> productIds,
                                         @RequestParam("userId") Long userId,
                                         @RequestParam Map<Long,Double> productPriceMap2,
                                         Model model) {
        if(productIds==null){
            StringBuilder product = new StringBuilder();
            for (Long product1 : viewFinalList) {
                Product p = productService.getProductById(product1);
                if (product.length() > 0 && viewFinalProductList.contains(p)) {
                    product.append("&");
                }
                product.append("selectedProducts=").append(product1);
            }
            return "redirect:/viewFinalCart?userId=" + userId + "&" + product.toString()+ "&" + "productId=" + product;
        }
        List<Product> selectedProductList = productService.getProductsById(productIds);
        model.addAttribute("selectedProductList", selectedProductList);
        model.addAttribute("productIds", productIds);
        model.addAttribute("userId", userId);
        model.addAttribute("productPriceMap1",productPriceMap2);
//        System.out.println(productPriceMap2+"submit");
        return "cartForm";
    }
    
    @RequestMapping("checkQuantity")
    public String checkQuantity(@RequestParam("productIds")List<Long> productIds,
                                @RequestParam("productId") Long productId,
                                @RequestParam("userId") Long userId,
                                @RequestParam("stockQuantity") int stockQuantity,
                                @RequestParam  Map<Long,Double> productPriceMap2,
                                Model model) {
        if (productService.checkQuantity(stockQuantity, productId, userId, selectedProductList, productIds, viewFinalList, viewFinalProductList)) {
            List<Product>viewFinal=productService.getProduct(productId);
            viewFinalProductList = productService.getFinalProducts(viewFinalList,productId,stockQuantity,selectedProductList,productPriceMap2);
//            System.out.println(productPriceMap2+"check");
            model.addAttribute("viewFinal",viewFinal);
            model.addAttribute("selectedProducts",selectedProductList);
            model.addAttribute("productId",productId);
            model.addAttribute("userId",userId);
            model.addAttribute("stockQuantity",stockQuantity);
            model.addAttribute("productIds",productIds);
            model.addAttribute("viewFinalProductList",viewFinalProductList);
            model.addAttribute("productPriceMap2",productPriceMap2);
            return "viewFinalCart";
        }
       else{
           return "invalidStockQuantity";
        }
    }

    @RequestMapping("removeCartProduct")
    public String removeCartProduct(@RequestParam("selectedProducts") List<Long> productIds,
                                    @RequestParam("productId") Long productId,
                                    @RequestParam("userId") Long userId,
                                    Model model){

        List<Long> updatedProductList = productService.removeCartProduct(productIds,productId,userId);
        model.addAttribute("selectedProducts",updatedProductList);
        model.addAttribute("productId",productId);
        model.addAttribute("userId",userId);
        StringBuilder updatedProductsParam = new StringBuilder();
        for (Long id : updatedProductList) {
            if (updatedProductsParam.length() > 0) {
                updatedProductsParam.append("&");
            }
            updatedProductsParam.append("selectedProducts=").append(id);
        }
        return "redirect:/submitSelectedProducts?userId=" + userId + "&" + updatedProductsParam.toString()+ "&" + "productId=" + productId;
    }

//    @RequestMapping("addToCart")
//    public String addToCart(@RequestParam("productId")Long productId, @RequestParam("userId")Long userId,Model model){
//
//        model.addAttribute("productId",productId);
//        model.addAttribute("userId",userId);
//        return "addToCartForm";
//    }

    @RequestMapping("adminDashboard")
    public String adminDashboard(@RequestParam("adminId") Long adminId, Model model){
        AdminDTO adminDTO = userService.getAdminById(adminId);
        if(Objects.nonNull(adminDTO)){
            List<Category> categoryList = categoryService.getAllCategory();
            model.addAttribute("categoryList",categoryList);
            model.addAttribute("admin",adminDTO);
        }
        return "adminDashboard";
    }

    @RequestMapping("addCategory")
    public String addCategory(@RequestParam("adminId") Long adminId,Model model){
        CategoryDTO categoryDTO = new CategoryDTO();
        model.addAttribute("categoryDTO",categoryDTO);
        model.addAttribute("adminId",adminId);
        return "addCategoryForm";
    }

    @RequestMapping("saveCategory")
    public String saveCategory (@RequestParam("adminId") Long adminId,@ModelAttribute("categoryDTO") CategoryDTO categoryDTO,Model model){
        userService.saveCategory(categoryDTO);
        model.addAttribute("adminId",adminId);
        return "redirect:/adminDashboard?adminId="+adminId;
    }

    @RequestMapping("showProductList")
    public String showProductList(@RequestParam("categoryId")Long categoryId,@RequestParam("adminId")Long adminId,Model model){
        CategoryDTO categoryDTO = userService.getCategoryById(categoryId);
        List<ProductDTO> productDTOList = productService.getProductsByCategoryId(categoryId);
        model.addAttribute("productList",productDTOList);
        model.addAttribute("category",categoryDTO);
        model.addAttribute("categoryId",categoryId);
        model.addAttribute("adminId",adminId);
        return "showProductListForm";
    }

}
