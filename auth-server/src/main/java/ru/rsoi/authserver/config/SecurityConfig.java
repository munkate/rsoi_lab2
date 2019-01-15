package ru.rsoi.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import ru.rsoi.authserver.service.CustomUserDetailsService;

import javax.sql.DataSource;


@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /*  @Autowired

    private ClientDetailsService clientDetailsService;*/
  @Autowired
  private DataSource dataSource;
    @Qualifier("customUserDetailsService")
    @Autowired
    private  CustomUserDetailsService userDetailsService;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(12));
       // auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
       // auth.jdbcAuthentication().dataSource(dataSource);

    }
 @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/setTime");
  }
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

 @Override

    protected void configure(HttpSecurity http) throws Exception {

     http.anonymous().and().formLogin();
 }
/* @Bean
    public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() throws Exception {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader("X-AUTH-TOKEN");
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager());
        requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(false);

        return requestHeaderAuthenticationFilter;
    }
    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService()));

        return preAuthenticatedAuthenticationProvider;
    }

*/
   /* @Override

    @Bean

    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }/*

    @Bean

    public TokenStore tokenStore() {

        return new InMemoryTokenStore();

    }
    @Bean

    @Autowired

    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){

        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();

        handler.setTokenStore(tokenStore);

        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));

        handler.setClientDetailsService(clientDetailsService);

        return handler;

    }

    @Bean

    @Autowired

    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {

        TokenApprovalStore store = new TokenApprovalStore();

        store.setTokenStore(tokenStore);

        return store;

    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }*/
}

