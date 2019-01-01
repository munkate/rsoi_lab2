package ru.rsoi.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rsoi.authserver.model.UserModel;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel result = this.userService.getUserByLogin(username);

        if(result!=null) {

            return User.withDefaultPasswordEncoder()
                    .username(result.getLogin())
                    .password(result.getPassword())
                    .roles("ROLE_USER")
                    .build();
        } else throw new UsernameNotFoundException("Пользователь не найден");
    }
}
