package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Создаем in-memory пользователя для примера
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user").password("{noop}password").roles("USER").build(),
                User.withUsername("admin").password("{noop}admin").roles("ADMIN").build()
        );
    }

    // Настроим фильтр безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login").permitAll()  // Разрешаем доступ ко странице логина
                .anyRequest().authenticated()         // Для всех остальных страниц требуется аутентификация
                .and()
                .formLogin()
                .loginPage("/login")                    // Страница логина
                .loginProcessingUrl("/login")           // URL для обработки логина
                .defaultSuccessUrl("/home", true)       // Страница после успешного входа
                .failureUrl("/login?error=true")        // Страница при ошибке входа
                .and()
                .logout()
                .permitAll();                         // Разрешаем всем выходить из системы

        return http.build();
    }
}
