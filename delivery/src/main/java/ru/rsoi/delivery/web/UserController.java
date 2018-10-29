package ru.rsoi.delivery.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.delivery.model.UserModel;
import ru.rsoi.delivery.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public UserModel ShipById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserModel> findAllShips() {
        return userService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShip(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    @PostMapping("/createuser")
    public void createUser(@RequestBody UserModel model) {
        userService.createUser(model);
    }

    @PostMapping("/edituser")
    public void editUser(@RequestBody UserModel model) {
        userService.editUser(model);
    }

}
