package repository.custom.impl;

import entity.ProductsEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.UserRepository;
import util.CrudUtil;
import util.UserRoles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public String findLastProductId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM user_entity ORDER BY created_at DESC LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserEntity checkUsernameForLogin(String username) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user_entity WHERE username=?", username);
            if (resultSet.next()) {
                return new UserEntity(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        UserRoles.valueOf(resultSet.getString(6))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean updateLoginTime(LocalDateTime dateTime,String id) {
        try {
            CrudUtil.execute("UPDATE user_entity SET last_login_at=? WHERE id=? ",
                    dateTime,id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    @Override
    public boolean add(UserEntity entity) {
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
            new Alert(Alert.AlertType.ERROR, "db error").show();
            return false;
        }
    }

    @Override
    public boolean update(UserEntity entity) {
        try {
            CrudUtil.execute("UPDATE user_entity SET username=?  ,role=? , email=? , phone_number=? ,  WHERE id=?",
                    entity.getUsername(),
                    entity.getRole(),
                    entity.getEmail(),
                    entity.getPhoneNumber(),
                    entity.getId()
            );
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {
            CrudUtil.execute("DELETE FROM user_entity WHERE id='" + id + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public UserEntity searchById(String id) {
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT * FROM user_entity WHERE id='"+id+"'");
            while (resultSet.next()){
                return new UserEntity(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        UserRoles.valueOf(resultSet.getString("role")),
                        resultSet.getObject("created_at", LocalDateTime.class),
                        resultSet.getObject("last_login_at", LocalDateTime.class)
                );
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"database error").show();
        }
        return null;
    }

    @Override
    public ObservableList<UserEntity> getAll() {
        ObservableList<UserEntity> allUserEntityList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user_entity");
            while (resultSet.next()) {
                allUserEntityList.add(new UserEntity(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        UserRoles.valueOf(resultSet.getString("role")),
                        resultSet.getObject("created_at", LocalDateTime.class),
                        resultSet.getObject("last_login_at", LocalDateTime.class)
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"database error").show();
        }
        return allUserEntityList;
    }
}
