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
    private Double balance;
    private List<OrderDetails> ordersDetails;

}
