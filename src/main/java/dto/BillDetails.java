package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillDetails {
    private Double totalAmount;
    private Double totalDiscount;
    private Double netTotalAmount;

}
