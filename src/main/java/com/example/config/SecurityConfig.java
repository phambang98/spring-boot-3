package com.example.config;

import com.example.model.CustomOAuth2Client;
import com.example.security.jwt.JWTConfigurer;
import com.example.security.jwt.TokenProvider;
import com.example.service.ClientService;
import com.example.service.CustomOAuth2ClientService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private ClientService clientService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomOAuth2ClientService customOAuth2ClientService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/ping", "/api/image2").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic().and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2ClientService)
                .and()
                .successHandler((request, response, authentication) -> {
                    System.out.println("AuthenticationSuccessHandler invoked");
                    System.out.println("Authentication name: " + authentication.getName());
                    CustomOAuth2Client oauthUser = (CustomOAuth2Client) authentication.getPrincipal();
                    clientService.processOAuthPostLogin(oauthUser);
                    String jwt = tokenProvider.createToken(authentication, false);
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(jwt);
                    out.flush();
                }).failureHandler((request, response, authentication) -> {
                    System.out.println("AuthenticationFailureHandler invoked");
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print("Login failure!");
                    out.flush();

                }).and()
                .headers().frameOptions().disable().and()
                .apply(securityConfigurerAdapter()).and().build();
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

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }


}
