package com.projectnoly.Configuration;

import com.projectnoly.Services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService,PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/superadmin/**").hasRole("SUPERADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN","SUPERADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN","SUPERADMIN")
                .requestMatchers("/login","/recoverPassword","/add-user","css/**","js/login.js","js/newUserJS.js","img/**").permitAll()
                .anyRequest().authenticated()
        )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )
                .exceptionHandling(exceptions ->exceptions
                        .accessDeniedPage("/access-denied"))
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();
    }
}
