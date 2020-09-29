package com.lambdaschool.apollo.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

@Profile("SECURITY_MOCK")
@Configuration
public class OktaAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                        .antMatchers("*")
                        .permitAll();

        // process CORS annotations
        http.cors().and().csrf().disable();

        // h2 console
        http.headers().frameOptions().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws
            Exception {
        return super.authenticationManagerBean();
    }

    @Resource(name = "securityUserService")
    private UserDetailsService userDetailsService;
}