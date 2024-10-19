package com.example.Real_Store.service.serviceImpl;

import com.example.Real_Store.conversion.ConversionClass;
import com.example.Real_Store.dto.ProductDTO;
//import com.example.Real_Store.entity.Booking;
import com.example.Real_Store.entity.Cart;
import com.example.Real_Store.entity.Category;
//import com.example.Real_Store.entity.Customer;
import com.example.Real_Store.entity.Product;
import com.example.Real_Store.enumeration.StockStatus;
import com.example.Real_Store.repository.CartRepository;
import com.example.Real_Store.repository.CategoryRepository;
//import com.example.Real_Store.repository.CustomerRepository;
//import com.example.Real_Store.repository.OrderRepository;
import com.example.Real_Store.repository.ProductRepository;
import com.example.Real_Store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.Real_Store.constants.RealStoreConstants.ACTIVE;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    public ConversionClass conversionClass;
    @Autowired
    public CategoryRepository categoryRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public CartRepository cartRepository;


    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {


        List<Product> productList = productRepository.
                findAll().stream().
                filter(product -> product.getCategory().getCategoryId()==categoryId).collect(Collectors.toList());
        return productList.stream().map(product -> conversionClass.Product_To_ProductDTO(product)).toList();
    }

    @Override
    public void saveProduct(ProductDTO productDTO,Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        Product product = conversionClass.ProductDTO_To_Product(productDTO);
        product.setCategory(category);
        product.setProductStatus(ACTIVE);
        product.setStockStatus(StockStatus.In_Stock);
        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsById(List<Long> productIds) {
        List<Product> productList = new ArrayList<>();
        productIds.stream().forEach(productId -> {
            Product product = productRepository.findById(productId).get();
            productList.add(product);
        });
        return productList;
    }

    @Override
    public boolean checkQuantity(int stockQuantity, Long productId, Long userId, List<Product> selectedProductList, List<Long> productIds, List<Long> viewFinalList, List<Product> viewFinalProductList) {
        Product product = productRepository.findById(productId).get();

        List<Product> productList1 = new ArrayList<>();
        if(product.getStockQuantity() >= stockQuantity){
            productIds.remove(productId);
            viewFinalList.add(productId);
            selectedProductList.add(product);
            Cart cart = new Cart();
            productList1.add(product);
            cart.setProductList(productList1);
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setRequiredQuantity(stockQuantity);
            cartRepository.save(cart);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Long> removeCartProduct(List<Long> productIds, Long productId, Long userId) {
        productIds.remove(productId);
        return productIds;
    }

    @Override
    public List<Product> getProduct(Long productId) {
        List<Product>list = productRepository.findById(productId).stream().toList();
        return list;
    }

    @Override
    public List<Product> getFinalProducts(List<Long> viewFinalList, Long selectedProductId, int stockQuantity, List<Product> selectedProductList, Map<Long, Double> map) {
        List<Product> output = new ArrayList<>();
        for(Product p : selectedProductList){
            Double productAmount = stockQuantity*p.getPrice();
            map.put(selectedProductId,productAmount);
            for (Map.Entry<Long,Double> entry : map.entrySet()){
                System.out.print("Product ID: " + entry.getKey() + ", Total Amount: " + entry.getValue());
            }
            if(p.getProductId().equals(selectedProductId) ){
                p.setStockQuantity(stockQuantity);
                output.add(p);
            }else {
                output.add(p);
            }
        }
        return output;
    }

    @Override
    public Product getProductById(Long product1) {
        return productRepository.findById(product1).get();
    }
}
