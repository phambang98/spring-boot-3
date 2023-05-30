package com.example.springchat.config;

import com.example.springchat.security.OAuth2FailureHandler;
import com.example.springchat.security.Oauth2SuccessHandler;
import com.example.springchat.security.jwt.TokenAuthenticationFilter;
import com.example.springchat.service.CustomOAuth2UsersService;
import com.example.springchat.ultis.OAuth2RequestRepository;
import com.example.springchat.ultis.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomOAuth2UsersService customOAuth2UsersService;

    @Autowired
    private Oauth2SuccessHandler oauth2SuccessHandler;

    @Autowired
    private OAuth2RequestRepository oAuth2RequestRepository;

    @Autowired
    private OAuth2FailureHandler auth2FailureHandler;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().authorizeHttpRequests()
                .requestMatchers("/", "/error", "/favicon.ico", "/*/*.png", "/*/*.gif", "/*/*.svg", "/*/*.jpg", "/*/*.html", "/*/*.css", "/*/*.js").permitAll()
                .requestMatchers("/api/account/*", "/api/docs", "/login/oauth2/code/*", "/ws/**","/api/file/**").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oauth2Login -> {
                    oauth2Login.authorizationEndpoint(it -> {
                        it.authorizationRequestRepository(oAuth2RequestRepository);
                    });
                    oauth2Login.userInfoEndpoint(it -> {
                        it.userService(customOAuth2UsersService);
                    });
                    oauth2Login.successHandler(oauth2SuccessHandler).failureHandler(auth2FailureHandler);
                })
                .headers().frameOptions().disable().and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
