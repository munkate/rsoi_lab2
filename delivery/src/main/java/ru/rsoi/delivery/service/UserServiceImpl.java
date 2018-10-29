package ru.rsoi.delivery.service;

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
        new_user.setAddress(user.getAddress());
        new_user.setBank(user.getBank());
        new_user.setFirst_name(user.getFirst_name());
        new_user.setLast_name(user.getLast_name());
        new_user.setSecond_name(user.getSecond_name());
        new_user.setInn(user.getInn());
        new_user.setUid(user.getUid());
        userRepository.saveAndFlush(new_user);

    }

    @NonNull
    private UserModel buildUserModel(User user) {
        UserModel model = new UserModel();
        model.setFirst_name(user.getFirst_name());
        model.setLast_name(user.getLast_name());
        model.setSecond_name(user.getSecond_name());
        model.setAddress(user.getAddress());
        model.setBank(user.getBank());
        model.setInn(user.getInn());
        model.setUid(user.getUid());
        return model;
    }

    @NonNull
    private User getEntity(UserModel model) {
        User user = userRepository.findByUid(model.getUid());
        return user;
    }

}
