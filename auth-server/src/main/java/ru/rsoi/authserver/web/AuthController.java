package ru.rsoi.authserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.userdetails.User;
import ru.rsoi.authserver.repository.UserRepository;
import ru.rsoi.authserver.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

@RestController
/*@RequestMapping("/")*/
@CrossOrigin
public class AuthController {
    @Autowired
    UserRepository repository;
    @Autowired
    UserService service;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {

      return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        Collection authorities = new ArrayList();
        ((ArrayList) authorities).add("read");


        ru.rsoi.authserver.entity.User user = repository.findByUid(id);
        User result = new User(user.getLogin(), user.getPassword(), authorities);
        return ResponseEntity.ok(result);
    }
}

