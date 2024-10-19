package com.example.Real_Store.service.serviceImpl;

//import com.example.Real_Store.entity.Payment;
import com.example.Real_Store.entity.Product;
import com.example.Real_Store.repository.OrderRepository;
//import com.example.Real_Store.repository.PaymentRepository;
import com.example.Real_Store.repository.ProductRepository;
import com.example.Real_Store.service.OrderService;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

        import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public OrderRepository orderRepository;
    List<Double> viewList = new ArrayList<>();



//    @Override
//    public List<Double> placeOrder(List<Product> viewFinalProductList) {
////        double totalAmount = 0;
////        for (Product product : viewFinalProductList) {
////            totalAmount += product.getPrice() * product.getStockQuantity();  // Accumulate the total
////            viewList.add(totalAmount);
////        }
////        return viewList;
//
//    }

//    @Autowired
//    public PaymentRepository paymentRepository

//    @Override
//    public String addProductToOrder(List<ProductDTO> productDTOList) {
//        Booking booking = new Booking();
//        booking.setOrderStatus(PENDING);
//        booking.setPaymentStatus(PAYMENT_INPROGRESS);
//        booking.setProductList(new ArrayList<>());
//        booking.setDate(new Date());
//        orderRepository.save(booking);
//        double updateTotalAmount = 0.0;
//        for (ProductDTO productDTO : productDTOList) {
//            Product product = productRepository.findById(productDTO.getProductid()).get();
//            if (Objects.nonNull(product) && product.getProductStatus().equals(ACTIVE)) {
//                int totalQuantity = product.getStockQuantity();
//                int givenProductQuantity = productDTO.getStockQuantity();
//                if(givenProductQuantity > totalQuantity){
//                    return "Ordered Quantity is more than Stock Quantity";
//                }
//                product.setStockQuantity(totalQuantity - givenProductQuantity);
//
//                double productAmount = product.getPrice();
//                updateTotalAmount += productAmount * givenProductQuantity;
//                Booking booking1 = orderRepository.findById(booking.getOrderId()).get();
//                booking1.getProductList().add(product);
//                product.getBookingList().add(booking);
//            }
//        }
//        booking.setTotalAmount(updateTotalAmount);
//        orderRepository.save(booking);
//        return "Product is Successfully added to Order";
//    }
//
//    @Override
//    public String getOrderConfirm(Long orderId) {
//        Booking booking = orderRepository.findById(orderId).get();
//        Payment payment = new Payment();
//        payment.setPaymentAmount(booking.getTotalAmount());
//        payment.setPaymentDate(new Date());
//        payment.setPaymentMethod(UPI);
//        payment.setPaymentStatus(PAYMENT_REJECTED);
//        if(paymentStatus(payment,booking)){
//            payment.setBooking(booking);
//            paymentRepository.save(payment);
//            return "Order is Confirmed";
//        }else {
//            payment.setBooking(booking);
//            paymentRepository.save(payment);
//            return "Order is not Confirmed";
//
//        }
//    }
//
//    public boolean paymentStatus(Payment payment,Booking booking){
//        if(payment.getPaymentStatus().equals(PAYMENT_SUCCESFUL)){
//            booking.setOrderStatus(ORDERED_SUCCESFUL);
//            booking.setPaymentStatus(PAYMENT_SUCCESFUL);
//
//            return true;
//        }else {
//            booking.setPaymentStatus(PAYMENT_REJECTED);
//            booking.setOrderStatus(ORDER_REJECTED);
//            payment.setPaymentStatus(PAYMENT_REJECTED);
//
//            return false;
//        }
//    }
}
