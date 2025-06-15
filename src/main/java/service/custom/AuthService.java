package service.custom;

import dto.LoginResponse;
import dto.UserLogin;
import dto.UserSignup;
import service.SuperService;
import util.UserRoles;

public interface AuthService extends SuperService {
    boolean signup(UserSignup signupData);
    String generateId(UserRoles role);
    LoginResponse login(UserLogin loginData);
}
