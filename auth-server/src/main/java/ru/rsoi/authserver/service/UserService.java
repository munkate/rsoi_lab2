package ru.rsoi.authserver.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.rsoi.authserver.model.UserModel;

import java.util.List;

public interface UserService {

    @Nullable
    UserModel getUserByUid(@NonNull long Id);

    List<UserModel> findAll();

    void editUser(UserModel user);

    void deleteUserById(@NonNull Integer id);

    void createUser(UserModel model);
    @Nullable
    UserModel getUserByLogin(String login);


}
