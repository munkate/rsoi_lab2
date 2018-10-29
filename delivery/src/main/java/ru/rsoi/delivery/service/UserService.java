package ru.rsoi.delivery.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.rsoi.delivery.model.UserModel;

import java.util.List;

public interface UserService {

    @Nullable
    UserModel getUserById(@NonNull Integer Id);

    List<UserModel> findAll();

    void editUser(UserModel user);

    void deleteUserById(@NonNull Integer id);

    void createUser(UserModel model);


}
