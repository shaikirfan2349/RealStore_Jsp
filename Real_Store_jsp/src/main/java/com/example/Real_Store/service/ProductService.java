package com.example.Real_Store.service;

import com.example.Real_Store.dto.ProductDTO;
import com.example.Real_Store.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
//    ProductDTO addProductToCategory(ProductDTO productDTO,Long categoryId) throws Exception;

//    ProductDTO getProductById(Long productId) throws Exception;

//    List<ProductDTO> getAllProduct();

//    String addProductToCustomer(Long productId, Long customerId) throws Exception;

//    String deleteProductById(Long productId);

//    ProductDTO updateProductByDTO(ProductDTO productDTO);

        List<ProductDTO> getProductsByCategoryId(Long categoryId);


    void saveProduct(ProductDTO productDTO, Long categoryId);

    List<Product> getAllProduct();

    List<Product> getProductsById(List<Long> productIds);

    boolean checkQuantity(int stockQuantity, Long productId, Long userId, List<Product> productList, List<Long> productIds, List<Long> viewFinalList, List<Product> viewFinalProductList);


    List<Long> removeCartProduct(List<Long> productIds, Long productId, Long userId);

    List<Product> getProduct(Long productId);


    List<Product> getFinalProducts(List<Long> viewFinalList, Long productId, int stockQuantity, List<Product> selectedProductList, Map<Long, Double> map);

    Product getProductById(Long product1);
}
