package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Orders {
    private String orderId;
    private String cashierId;
    private String customerId;
    private Double totalPrice;
    private Double totalDiscountAmount;
    private Double netTotalPrice;
    private Double paymentAmount;
    private Double balance;
    private List<OrderDetails>ordersDetails;


}
