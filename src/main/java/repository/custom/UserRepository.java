package repository.custom;

import entity.UserEntity;
import repository.CrudRepository;

import java.time.LocalDateTime;

public interface UserRepository extends CrudRepository<UserEntity,String> {
    String findLastProductId();
    UserEntity checkUsernameForLogin(String username);
    Boolean updateLoginTime(LocalDateTime dateTime ,String id);
//    void updateLastLoginTime(String username);

}
