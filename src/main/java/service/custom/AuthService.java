package service.custom;

import dto.LoginResponse;
import dto.UserLogin;
import dto.UserSignup;
import service.SuperService;
import util.UserRoles;

import java.sql.SQLException;

public interface AuthService extends SuperService {
    boolean signup(UserSignup signupData) throws SQLException;
    String generateId(UserRoles role);
    LoginResponse login(UserLogin loginData);
}
