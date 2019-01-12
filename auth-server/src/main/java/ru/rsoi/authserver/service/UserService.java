package ru.rsoi.authserver.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.rsoi.authserver.entity.User;
import ru.rsoi.authserver.model.UserModel;

import java.util.List;

public interface UserService {

    @Nullable
    UserModel getUserByUid(@NonNull long Id);

    List<UserModel> findAll();

    //void editUser(User user);

    void deleteUserById(@NonNull Integer id);
    @Nullable
    UserModel getUserByLogin(String login);

    String getAccessToken(String login, String password);


}
