package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailsEntity {
    private String orderId;
    private String productId;
    private String productName;
    private Long qty;
    private Double price;
    private Double totalPrice;
    private  Double discount;
}
