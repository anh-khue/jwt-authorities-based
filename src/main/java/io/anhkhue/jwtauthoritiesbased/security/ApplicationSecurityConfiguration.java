package io.anhkhue.jwtauthoritiesbased.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static io.anhkhue.jwtauthoritiesbased.security.SecurityConstants.*;

@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
            .antMatchers(HttpMethod.GET, "/tasks/**").permitAll()
            .antMatchers(HttpMethod.GET, "/accounts/**").permitAll()
            .anyRequest().authenticated();
    }
}
