package repository.custom.impl;

import dto.UserLogin;
import entity.UserSignupEntity;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.UserRepository;
import util.CrudUtil;
import util.UserRoles;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public String findLastProductId() {
            try {
                ResultSet resultSet= CrudUtil.execute("SELECT id FROM user_entity ORDER BY created_at DESC LIMIT 1");
                if(resultSet.next()){
                    return resultSet.getString("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public UserSignupEntity checkUsernameForLogin(String username) {
        try {
           ResultSet resultSet= CrudUtil.execute("SELECT * FROM user_entity WHERE username=?",username);
           if (resultSet.next()){
               return  new UserSignupEntity(
                       resultSet.getString("id"),
                       resultSet.getString("username"),
                       resultSet.getString("password"),
                       UserRoles.valueOf( resultSet.getString(6))
               );
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public boolean add(UserSignupEntity entity) {
        try {
            CrudUtil.execute("INSERT INTO user_entity VALUES(?,?,?,?,?,?,?,?)",
                    entity.getId(),
                    entity.getUsername(),
                    entity.getPassword(),
                    entity.getEmail(),
                    entity.getPhoneNumber(),
                    entity.getRole().name(),
                    entity.getCreatedAt(),
                    entity.getLastLoginAt()
            );
            return true;
        } catch (SQLException e) {
          new Alert(Alert.AlertType.ERROR,"db error").show();
          return false;
        }
    }

    @Override
    public boolean update(UserSignupEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public UserSignupEntity searchById(String s) {
        return null;
    }

    @Override
    public ObservableList getAll() {
        return null;
    }
}
