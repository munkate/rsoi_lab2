package ru.rsoi.authserver.service;

import com.oracle.webservices.internal.api.message.ContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.rsoi.authserver.entity.User;
import ru.rsoi.authserver.model.UserModel;
import ru.rsoi.authserver.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::buildUserModel)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public UserModel getUserByUid(long id) {
        try{
            UserModel model = buildUserModel( userRepository.findByUid(id));
            return model;
        }
        catch(RuntimeException e)
        {

            return null;
        }
    }
/*@Override
    public JSONObject getAccessToken(HttpHeaders request)
    {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8083/deliveries/editdelivery");
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString((Map<String, ?>) delivery), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                LOGGER.info("Delivery updated");
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
    }*/
    @Nullable
    @Override
    public UserModel getUserByLogin(String login) {
        try{
            UserModel model = buildUserModel( userRepository.findByLogin(login));
            return model;
        }
        catch(RuntimeException e)
        {

            return null;
        }
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);

    }

    @Override
    public void createUser(UserModel model) {

        User user = new User(model.getLast_name(), model.getFirst_name(), model.getSecond_name(), model.getAddress(), model.getBank(), model.getInn(), model.getLogin(),model.getPassword());
        userRepository.save(user);

    }

    @Override
    public void editUser(UserModel user) {
        User new_user = getEntity(user);
        BeanUtils.copyProperties(user,new_user);
        userRepository.saveAndFlush(new_user);

    }

    @NonNull
    private UserModel buildUserModel(User user) {
        UserModel model = new UserModel();
        BeanUtils.copyProperties(user,model);
        return model;
    }

    @NonNull
    private User getEntity(UserModel model) {
        User user = userRepository.findByUid(model.getUid());
        return user;
    }

}
