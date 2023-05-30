package com.example.springmvc.config;


import com.example.springmvc.security.FormLoginFailureHandler;
import com.example.springmvc.security.FormLoginSuccessHandler;
import com.example.springmvc.security.Oauth2FailureHandler;
import com.example.springmvc.security.Oauth2SuccessHandler;
import com.example.springmvc.service.CustomOAuth2UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UsersService customOAuth2UsersService;
    @Autowired
    private FormLoginFailureHandler formLoginFailureHandler;

    @Autowired
    private FormLoginSuccessHandler formLoginSuccessHandler;

    @Autowired
    private Oauth2FailureHandler oauth2FailureHandler;

    @Autowired
    private Oauth2SuccessHandler successHandler;

    private static final String[] AUTH_WHITELIST = {
            "/login",
            "/templates/html/**",
            "/templates/css/**",
            "/templates/js/**",
            "/sign-up"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/image2").hasAuthority("dog")
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/login")
                .successHandler(formLoginSuccessHandler)
                .failureHandler(formLoginFailureHandler)
                .and()
                .httpBasic()
                .and()
                .oauth2Login()// enable form login default with oauth2
                .loginPage("/login")
                .authorizationEndpoint()
                .baseUri("/login/oauth2/authorization")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UsersService)
                .and()
                .successHandler(successHandler)
                .failureHandler(oauth2FailureHandler)
                .and()
                .headers().frameOptions().disable().and()
                .build();
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
