package ru.rsoi.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /*  @Autowired

    private ClientDetailsService clientDetailsService;*/

    @Autowired

    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()

                .withUser("bill").password(encoder.encode("abc123")).roles("ADMIN").and()

                .withUser("bob").password(encoder.encode("abc123")).roles("USER");

    }
    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
*/


/* @Override

    protected void configure(HttpSecurity http) throws Exception {


      http.antMatcher("/**")
              .authorizeRequests()
              .antMatchers("/", "/login**", "/oauth/authorize**").permitAll()
              .anyRequest().permitAll()
              .and().exceptionHandling()
      .and().csrf();
    }

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

