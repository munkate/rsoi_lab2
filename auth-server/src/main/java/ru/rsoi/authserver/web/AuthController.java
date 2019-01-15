package ru.rsoi.authserver.web;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONException;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.rsoi.authserver.entity.User;
import ru.rsoi.authserver.model.UserModel;
import ru.rsoi.authserver.repository.UserRepository;
import ru.rsoi.authserver.service.CustomUserDetailsService;
import ru.rsoi.authserver.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
//@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UserService service;
    @Autowired
    CustomUserDetailsService detailService;


   @GetMapping("/authentification")
   @PreAuthorize("permitAll()")
    public ResponseEntity<JSONObject> login(@RequestParam("login") String login, @RequestParam("password") String password,final HttpServletRequest request) throws Exception {
     String response = detailService.getAccessToken(login, password, request);
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
        detailService.setTime(token,accessedDate);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/checktoken")
    public ResponseEntity<Boolean> checkToken(@RequestHeader("token") String token)
    {
        return ResponseEntity.ok(detailService.checkToken(token));
    }
    @GetMapping("/context")
    public ResponseEntity<Authentication> getContext()
    {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }
    @GetMapping("/authorities")
    public ResponseEntity<JSONArray> getUserAuthorities(@RequestParam("login") String login, @RequestParam("password") String password){
       JSONArray response = new JSONArray();
       Object[] list = detailService.loadUserByUsername(login).getAuthorities().toArray();
       int i=0;
       while (i<list.length)
       {
           response.add(list[i]);
           i++;
       }
       return ResponseEntity.ok(response);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable long id) {
        UserModel user = service.getUserByUid(id);
        return ResponseEntity.ok(user);
    }
}

