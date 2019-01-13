package ru.rsoi.authserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONException;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.rsoi.authserver.entity.User;
import ru.rsoi.authserver.model.UserModel;
import ru.rsoi.authserver.repository.UserRepository;
import ru.rsoi.authserver.service.UserService;

import java.util.*;

@RestController
//@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UserService service;
    @Qualifier("customUserDetailsService")
    @Autowired
    UserDetailsService detailService;

   @GetMapping("/authentification")
   @PreAuthorize("permitAll()")
    public ResponseEntity<JSONObject> login(@RequestParam("login") String login, @RequestParam("password") String password) throws JSONException {
     String response = service.getAccessToken(login, password);
     JSONObject res = new JSONObject();
     if (response.equals("Неверный логин или пароль"))
     {
         res.put("error", "Неверный логин или пароль");
         return ResponseEntity.status(401).body(res);
     }
    else {
        res.put("token", response);
        return ResponseEntity.ok(res);
    }
    }
    @GetMapping("/setTime")
    public ResponseEntity<Void> setTime(@RequestParam("token") String token, @RequestParam("date") long accessedDate)
    {
        service.setTime(token,accessedDate);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable long id) {
        UserModel user = service.getUserByUid(id);
        return ResponseEntity.ok(user);
    }
}

