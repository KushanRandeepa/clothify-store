package entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ProductsEntity {
    private String id;
    private String name;
    private String category;
    private String size;
    private Long stock;
    private Double price;
    private Double discount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductsEntity(String id, String name, String category, String size, Long stock, Double price,Double discount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.size = size;
        this.stock = stock;
        this.price = price;
        this.discount=discount;
    }



}
