package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.UserRoles;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignup {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private UserRoles role;
}
