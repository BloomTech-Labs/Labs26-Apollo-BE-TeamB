package com.lambdaschool.apollo.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

    @Configuration
    public class OktaAuthSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                    if (System.getenv("IS_DEV") == null) {
                        http.authorizeRequests()
                            .antMatchers("/",
                                        "/h2-console/**",
                                        "/swagger-resources/**",
                                        "/swagger-resource/**",
                                        "/swagger-ui.html",
                                        "/v2/api-docs",
                                        "/webjars/**")
                                        .permitAll()
                                        .antMatchers("/users/**",
                                                "/topics/**",
                                                "/contexts/**",
                                                "/questions/**",
                                                "/surveys/**")
                                        .authenticated()
                                        .and()
                                        .oauth2ResourceServer().jwt();
                    } else {
                        http.authorizeRequests()
                                .antMatchers("*")
                                .permitAll();
                    }

            // process CORS annotations
            http.cors().and().csrf().disable();

            // force a non-empty response body for 401's to make the response more browser friendly
            Okta.configureResourceServer401ResponseBody(http);

            // h2 console
            http.headers().frameOptions().disable();
        }
    }