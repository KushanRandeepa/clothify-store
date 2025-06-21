package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String orderId;

    public OrderDetails(String productId, String productName, Long qty, Double price, Double totalPrice, Double discount) {
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
        this.price = price;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    private String productId;
    private String productName;
    private Long qty;
    private Double price;
    private Double totalPrice;
    private  Double discount;
}
