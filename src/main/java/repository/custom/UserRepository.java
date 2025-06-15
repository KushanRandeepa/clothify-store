package repository.custom;

import entity.UserSignupEntity;
import repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserSignupEntity ,String> {
    String findLastProductId();
    UserSignupEntity checkUsernameForLogin(String username);
//    void updateLastLoginTime(String username);

}
