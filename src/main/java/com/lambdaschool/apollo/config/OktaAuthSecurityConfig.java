package com.lambdaschool.apollo.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.core.env.Environment;

    @Configuration
    public class OktaAuthSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private Environment env;

        @Bean
        public TokenStore tokenStore()
        {
            return new InMemoryTokenStore();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2ResourceServer().jwt();
//                        .antMatchers("*")
//                        .permitAll();

            // process CORS annotations
            http.cors().and().csrf().disable();

            // force a non-empty response body for 401's to make the response more browser friendly
            Okta.configureResourceServer401ResponseBody(http);
        }
    }