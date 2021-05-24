package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/signup", "h2", "/login", "logout", "/css/**", "/js/**").permitAll().
                anyRequest().authenticated().
                and().
                formLogin().
                loginPage("/login").
                permitAll().
                and().
                logout().
                permitAll();

        http.formLogin().
                defaultSuccessUrl("/home", true);

        http.logout().
                logoutUrl("/logout").
                logoutSuccessUrl("/login?logout").
                invalidateHttpSession(false).
                deleteCookies("remove");

        http.sessionManagement().
                maximumSessions(3600).
                and().
                invalidSessionUrl("/login");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
