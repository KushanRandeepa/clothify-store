package service.custom.impl;

import dto.User;
import entity.UserEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.UserRepository;
import service.custom.UserManagerService;
import util.Repositorytype;

import java.time.LocalDateTime;

public class UserManagerServiceImpl implements UserManagerService {
    UserRepository userRepository= DaoFactory.getInstance().getRepositoryType(Repositorytype.USER);

    @Override
    public boolean updateUser(User user) {
        return userRepository.update(new ModelMapper().map(user, UserEntity.class));
    }

    @Override
    public boolean deleteUser(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public ObservableList<UserEntity> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public UserEntity searchUser(String id) {
        return userRepository.searchById(id);
    }

    @Override
    public boolean updateLoginTime(String id) {
       return userRepository.updateLoginTime(LocalDateTime.now(),id);

    }
}
