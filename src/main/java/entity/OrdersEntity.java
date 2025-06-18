package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class OrdersEntity {
    private String orderId;
    private String cashierId;
    private String customerEmail;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private Double totalPrice;
    private Double totalDiscountAmount;
    private Double netTotalPrice;
    private Double paymentAmount;
    private Double balance;

    public OrdersEntity(String orderId, String cashierId, LocalDate orderDate, LocalTime orderTime, Double totalPrice, Double totalDiscountAmount, Double netTotalPrice) {
        this.orderId = orderId;
        this.cashierId = cashierId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.totalDiscountAmount = totalDiscountAmount;
        this.netTotalPrice = netTotalPrice;
    }



}
