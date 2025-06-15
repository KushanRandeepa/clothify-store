
package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.UserRoles;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {
    private String id;
    private UserRoles role;
    private String username;

}
