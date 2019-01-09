package ru.rsoi.authserver.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.rsoi.authserver.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    @Nullable
    UserModel getUserByUid(@NonNull long Id);

    List<UserModel> findAll();

    //void editUser(User user);

    void deleteUserById(@NonNull Integer id);
    @Nullable
    UserModel getUserByLogin(String login);

    JSONObject getAccessToken(String login, String password) throws JSONException;

  //  CloseableHttpResponse getAccessToken(String code);


}
