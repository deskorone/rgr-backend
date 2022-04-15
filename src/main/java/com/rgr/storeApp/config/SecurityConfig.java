package com.rgr.storeApp.config;

import com.rgr.storeApp.exceptions.AutorizeExceptionHandler;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.secutity.filters.TokenFilterImpl;
import com.rgr.storeApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final TokenFilterImpl tokenFilter;
    private final AutorizeExceptionHandler autorizeExceptionHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, TokenFilterImpl tokenFilter, AutorizeExceptionHandler autorizeExceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenFilter = tokenFilter;
        this.autorizeExceptionHandler = autorizeExceptionHandler;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(autorizeExceptionHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**" ).permitAll()
                .antMatchers("/api/products/get/*").permitAll()
                .antMatchers("/api/products/get/photo/*").permitAll()
                .antMatchers("/api/check").permitAll()
                .antMatchers("/api/products/find/**").permitAll()
                .antMatchers("/api/accept/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
