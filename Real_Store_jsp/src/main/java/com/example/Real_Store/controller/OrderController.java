package com.example.Real_Store.controller;

import com.example.Real_Store.dto.BookingDTO;
import com.example.Real_Store.dto.ProductDTO;
import com.example.Real_Store.entity.Product;
import com.example.Real_Store.service.OrderService;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    @Autowired
    public OrderService orderService;

    @RequestMapping("placeOrder")
    public String placeOrder(@RequestParam("productPriceMap") Map<Long,Double> productPriceMap ,@RequestParam("viewFinalProductList") List<Product> viewFinalProductList, @RequestParam("userId")Long userId, Model model){

//        List<Double>viewList = orderService.placeOrder(viewFinalProductList);
        model.addAttribute("viewFinalProductList",viewFinalProductList);
//        model.addAttribute("viewList",viewList);

        return "placeOrder";
    }

//    @PostMapping ("/addProductToOrder")
//    public String  addProductToorder(@RequestBody List<ProductDTO> productDTOList){
//        return orderService.addProductToOrder(productDTOList);
//    }
//
//    @PostMapping("/getOrderConfirmation")
//    public String getOrderConfirmation(@RequestParam("orderId") Long orderId){
//        return orderService.getOrderConfirm(orderId);
//    }

    //delete
}
