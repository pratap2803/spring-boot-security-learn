package com.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/signin").permitAll()
                //.antMatchers("/home","/login", "/register").permitAll()
                //.antMatchers("/public/**").permitAll()
                .antMatchers("/public/**").hasRole("NORMAL")
                .antMatchers("/users/**").hasRole("ADMIN")
                /*
                if we are enabling prePost annotation then we do not need
                antMatchers()
                 */
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .formLogin()// for enabling form based login
                .loginPage("/signin");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("john").password(this.passwordEncoder().encode("durgesh")).roles("NORMAL");
        auth.inMemoryAuthentication().withUser("kabir").password(this.passwordEncoder().encode("preeti")).roles("ADMIN");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        //return NoOpPasswordEncoder.getInstance(); // no encoding used here to encrypt password
        return new BCryptPasswordEncoder(10);
    }
}
