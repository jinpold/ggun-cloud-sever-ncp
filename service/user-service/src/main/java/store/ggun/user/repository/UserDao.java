package store.ggun.user.repository;

import store.ggun.user.domain.UserDto;
import store.ggun.user.domain.UserModel;


public interface UserDao {
    void modify(UserDto userDto);

    UserModel modifyFind(UserDto userDto);


}
