package ru.rsoi.authserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.rsoi.authserver.entity.User;
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

    /*@GetMapping("/login")
    public ResponseEntity<Void> login(@RequestParam("code") String code) {
    service.getAccessToken(code);
      return ResponseEntity.ok().build();
    }*/

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetails> getUser(@PathVariable long id) {
        Collection authorities = new ArrayList();
        ((ArrayList) authorities).add("read");


        User user = repository.findByUid(id);
        org.springframework.security.core.userdetails.User result = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),authorities);
        return ResponseEntity.ok(result);
    }
}

