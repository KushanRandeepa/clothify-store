package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private String id;
    private String name;
    private String category;
    private String size;
    private Long stock;
    private Double price;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
}
