package entity;

import dto.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class OrdersEntity {
    private String orderId;
    private String cashierId;
    private String customerId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private Double totalPrice;
    private Double totalDiscountAmount;
    private Double netTotalPrice;
    private Double paymentAmount;

    public OrdersEntity(String orderId, String cashierId, String customerId, LocalDate orderDate, LocalTime orderTime, Double totalPrice, Double totalDiscountAmount, Double netTotalPrice, Double paymentAmount, Double balance) {
        this.orderId = orderId;
        this.cashierId = cashierId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.totalDiscountAmount = totalDiscountAmount;
        this.netTotalPrice = netTotalPrice;
        this.paymentAmount = paymentAmount;
        this.balance = balance;
    }

    private Double balance;
    private List<OrderDetails> ordersDetails;

}
