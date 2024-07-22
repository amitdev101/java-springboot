package com.example.ecommercesystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/actuator/**").permitAll() // Allow unrestricted access to actuator endpoints
            .anyRequest().permitAll()              // Allow unrestricted access to all other requests
            .and()
            .csrf().disable();                        // Disable CSRF protection for simplicity
    }
}
