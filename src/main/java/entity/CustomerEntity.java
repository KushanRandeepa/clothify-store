package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerEntity {

    private String id;
    private String name;
    private String phoneNumber;
    private String email;

}

