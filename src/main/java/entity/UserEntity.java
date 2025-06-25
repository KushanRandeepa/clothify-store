package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.UserRoles;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private UserRoles role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public UserEntity(String id, String username, String email, String phoneNumber, UserRoles role, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public UserEntity(String id, String username, String password, UserRoles role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
