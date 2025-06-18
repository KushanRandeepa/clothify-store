package dto;

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

public class Orders {
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

}
