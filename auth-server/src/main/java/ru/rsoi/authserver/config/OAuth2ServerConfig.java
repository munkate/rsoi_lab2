package ru.rsoi.authserver.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
@EnableAuthorizationServer
@Configuration
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
     //  clients.jdbc(this.dataSource);
        clients.inMemory().withClient("acme")
                .authorizedGrantTypes("client-credentials","authorization_code", "password","refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_ANDROID_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .redirectUris("localhost:8085/api/login")
                .accessTokenValiditySeconds(3600)
                .secret(passwordEncoder().encode("acmesecret")).refreshTokenValiditySeconds(50000);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(this.tokenStore());
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(this.passwordEncoder());
    }

   /* @Autowired
    private AuthenticationManager authenticationManager;
    @Qualifier("customUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private Environment env;
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("acme")
                .authorizedGrantTypes("client-credentials","authorization_code", "password","refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_ANDROID_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .redirectUris("localhost:8085/api/login")
                .accessTokenValiditySeconds(3600)
                .secret("acmesecret").refreshTokenValiditySeconds(50000);
    }/*

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(new InMemoryTokenStore())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenEnhancer(new CustomTokenEnhancer());

    }
    *//*@Override

    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(defaultAccessTokenConverter())

                .userDetailsService(userDetailsService);

    }

    @Bean

    public TokenStore tokenStore(){

        return new JwtTokenStore(defaultAccessTokenConverter());

    }

    @Bean

    public JwtAccessTokenConverter defaultAccessTokenConverter() {

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        converter.setSigningKey("123");

        return converter;

    }*//*


   *//* @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/authdb");
        dataSource.setUsername("kate");
        dataSource.setPassword("031226");
        return dataSource;
    }*//*
*//*
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer()));
        endpoints.tokenStore(new InMemoryTokenStore()).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
    }*//*
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {

        builder.userDetailsService(userDetailsService);
    }
*/

}
