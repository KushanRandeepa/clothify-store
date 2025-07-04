package service.custom;

import dto.User;
import entity.UserEntity;
import javafx.collections.ObservableList;
import service.SuperService;

public interface UserManagerService extends SuperService {
    boolean updateUser(User user);
    boolean deleteUser(String id);
    ObservableList<User> getAllUsers();
    User searchUser(String id);
    boolean updateLoginTime(String id);
}
