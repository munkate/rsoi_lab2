package ru.rsoi.authserver.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import ru.rsoi.authserver.config.SecurityConfig;
import ru.rsoi.authserver.model.UserModel;
import ru.rsoi.authserver.model.UserToken;
import ru.rsoi.authserver.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Resource(name="authenticationManager")
    private AuthenticationManager authManager;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel result = this.userService.getUserByLogin(username);

        if(result!=null) {

            return User.builder()
                    .username(result.getLogin())
                    .password(result.getPassword())
                    .roles(result.getAuthorities())
                    .build();
        } else throw new UsernameNotFoundException("User not found");
    }

    public String getAccessToken(String login, String password, HttpServletRequest request) throws Exception {
        UserDetails user = loadUserByUsername(login);
        if (user!=null){

            if (BCrypt.checkpw(password,user.getPassword()))
            {
                UserToken token = new UserToken();

                userRepository.setToken(token.getValue(),token.getValidity(),new Timestamp(System.currentTimeMillis()), user.getUsername());

                UsernamePasswordAuthenticationToken authReq =
                        new UsernamePasswordAuthenticationToken(login, password);
                Authentication auth = authManager.authenticate(authReq);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
                ensureSessionHasSecurityContext(request);
                return token.getValue();

            }
            else return "Неверный логин или пароль.";
        }
        else return "Пользователь не зарегистрирован.";
    }
    private void ensureSessionHasSecurityContext(HttpServletRequest hreq) {
        HttpSession session = hreq.getSession(true);
        Object securityContext = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext == null) {
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        }
    }

    public void setTime(String token, long accessDate) {
        if (checkToken(token))
        {
            Timestamp oldDate = userRepository.getTime(token);
            if (accessDate - oldDate.getTime()<1800000)
            {userRepository.setTime(new Timestamp(accessDate), token);}
            else {userRepository.setDisabled(token);}
        }
    }


    public boolean checkToken(String token) {
        ru.rsoi.authserver.entity.User user = userRepository.findByToken(token);
        return user.isEnabled();
    }
}
