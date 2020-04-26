package com.njhyuk.first.springboot.config.auth;

import com.njhyuk.first.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        h2ConsoleConfig(http);
        authorizeRequestsConfig(http);
        logoutConfig(http);
        userInfoConfig(http);
    }

    private void h2ConsoleConfig(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and();
    }

    private void authorizeRequestsConfig(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile")
                .permitAll()
                .antMatchers("/api/v1/**")
                .hasRole(Role.USER.name())
                .anyRequest()
                .authenticated()
                .and();

    }

    private void logoutConfig(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutSuccessUrl("/");
    }

    private void userInfoConfig(HttpSecurity http) throws Exception {
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
