package ru.rsoi.delivery.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.rsoi.delivery.entity.User;
import ru.rsoi.delivery.model.UserModel;
import ru.rsoi.delivery.repository.UserRepository;

import java.util.List;
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
    public UserModel getUserById(Integer id) {
        return userRepository.findById(id).map(this::buildUserModel).orElse(null);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);

    }

    @Override
    public void createUser(UserModel model) {

        User user = new User(model.getLast_name(), model.getFirst_name(), model.getSecond_name(), model.getAddress(), model.getBank(), model.getInn());
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
