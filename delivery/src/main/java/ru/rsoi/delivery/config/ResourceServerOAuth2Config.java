package ru.rsoi.delivery.config;

import org.springframework.context.annotation.Configuration;

@Configuration
/*@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled=true)*/
public class ResourceServerOAuth2Config /*extends ResourceServerConfigurerAdapter*/ {
    /*private static final String RESOURCE_ID = "deliveries";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                *//*.antMatchers("/users/**").authenticated()*//*
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:8085/api/oauth/check_token");
        tokenService.setClientId("acme");
        tokenService.setClientSecret("111");
        return tokenService;
    }*/
}