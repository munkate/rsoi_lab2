package ru.rsoi.authserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.rsoi.authserver.entity.User;
import ru.rsoi.authserver.model.UserModel;
import ru.rsoi.authserver.model.UserToken;
import ru.rsoi.authserver.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

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
    public CloseableHttpResponse getAccessToken(String code)
    {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/oauth/token?client_id=acme&client_secret=acmesecret&grant_type=authorization_code&code="+code+"&redirect_uri=example.ru");

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                LOGGER.info("Delivery updated");
                return httpClient.execute(httpPost);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
            return null;
        }

    }*/
    @Nullable
    @Override
    public UserModel getUserByLogin(String login) {
        try{
            UserModel model = buildUserModel(userRepository.findByLogin(login));
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


/*
    @Override
    public void editUser(User user) {
        User new_user = getEntity(user);
        BeanUtils.copyProperties(user,new_user);
        userRepository.saveAndFlush(new_user);

    }*/

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
