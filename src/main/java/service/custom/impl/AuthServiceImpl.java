package service.custom.impl;

import dto.LoginResponse;
import dto.UserLogin;
import dto.UserSignup;
import entity.UserSignupEntity;
import org.jasypt.util.text.BasicTextEncryptor;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.UserRepository;
import service.custom.AuthService;
import util.Repositorytype;
import util.UserRoles;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AuthServiceImpl implements AuthService {
    String secretKey="yrgGTg#3232t%b7382x8ruhRn753vx";
    BasicTextEncryptor textEncryptor;

    public AuthServiceImpl() {
      this.textEncryptor=new BasicTextEncryptor();
      textEncryptor.setPassword(secretKey);
    }

    UserRepository repository= DaoFactory.getInstance().getRepositoryType(Repositorytype.USER);

    @Override
    public boolean signup(UserSignup signupData) throws SQLException {
        UserSignupEntity entity = new ModelMapper().map(signupData, UserSignupEntity.class);
        entity.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        entity.setId(generateId(entity.getRole()));
        entity.setPassword(textEncryptor.encrypt(signupData.getPassword()));
        return repository.add(entity);


    }

    @Override
    public String generateId(UserRoles role) {
        String lastId = repository.findLastProductId();
        int number=0;

        if (lastId!=null) {
            try {
                number = Integer.parseInt(lastId.replaceAll("[^0-9]", ""));

            } catch (NumberFormatException e) {

                number = 0;
            }
        }
            number++;

            switch (role) {
                case ADMIN:return String.format("AD%03d", number);
                case CASHIER:return String.format("CA%03d", number);
//                case EMPLOYEE:return String.format("EM%03d", number);
                case SUPPLIER:return String.format("SPP%03d", number);
                default:
                    throw new IllegalArgumentException("Unsupported role: " + role);
            }
        }

    @Override
    public LoginResponse login(UserLogin loginData) {
        UserSignupEntity entity = repository.checkUsernameForLogin(loginData.getUsername());

        if(entity==null) return null;
        String decryptPassword = textEncryptor.decrypt(entity.getPassword());

        if(decryptPassword.equals(loginData.getPassword())) {
            return new LoginResponse(entity.getId(),entity.getRole(),entity.getUsername());
        }
        return null;
    }


}

