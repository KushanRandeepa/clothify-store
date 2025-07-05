package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardTables {
    private String id;
    private String name;
    private String number;
    private String email;
    private Double amount;
    private Integer orders;

}
