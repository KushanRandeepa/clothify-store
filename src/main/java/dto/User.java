package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.UserRoles;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class User {

    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private UserRoles role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public User(String username, String password, String email, String phoneNumber, UserRoles role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
    public User(String id, String username, String email, String phoneNumber, UserRoles role, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;






    }
}

