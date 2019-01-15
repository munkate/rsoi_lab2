package ru.rsoi.authserver.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.sql.DataSource;
@EnableAuthorizationServer
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
   /* @Autowired
    private AuthenticationManager authenticationManager;
*/

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory cf;
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(cf);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
     clients.jdbc(this.dataSource);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(this.tokenStore());
    }


    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(this.passwordEncoder());
        security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
    }

}
