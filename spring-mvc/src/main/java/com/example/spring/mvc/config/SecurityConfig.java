package com.example.spring.mvc.config;


import com.example.spring.mvc.security.FormLoginFailureHandler;
import com.example.spring.mvc.security.FormLoginSuccessHandler;
import com.example.spring.mvc.security.Oauth2FailureHandler;
import com.example.spring.mvc.security.Oauth2SuccessHandler;
import com.example.spring.mvc.service.CustomOAuth2UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspection) throws Exception {
        MvcRequestMatcher.Builder mvcRequestMatchers = new MvcRequestMatcher.Builder(introspection);
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers(
                        mvcRequestMatchers.pattern("/login"), mvcRequestMatchers.pattern("/templates/html/**"),
                        mvcRequestMatchers.pattern("/templates/css/**"), mvcRequestMatchers.pattern("/templates/js/**"),
                        mvcRequestMatchers.pattern(HttpMethod.POST, "/sign-up")).permitAll()
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
