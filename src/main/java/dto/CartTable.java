package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

@NoArgsConstructor
public class CartTable {
    private String id;
    private String name;
    private Long qty;
    private Double price;
    private Double discount;
    private Double totalAmount;
    private Double totalDiscount;
    private Double netTotalAmount;

    public CartTable(String id, String name, Long qty, Double price, Double discount, Double netTotalAmount) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.discount = discount;
        this.netTotalAmount = netTotalAmount;
    }

    public CartTable( Double totalAmount, Double totalDiscount, Double netTotalAmount) {

        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.netTotalAmount = netTotalAmount;
    }


}
