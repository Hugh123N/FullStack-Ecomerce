package com.curso.ecomerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringBootSecurity {

    @Bean
    public SecurityFilterChain configureHttp(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Deshabilita CSRF
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/css/**", "/vendor/**", "/images/**").permitAll()
                                .requestMatchers("/administrador/**").hasAuthority("ADMIN") // Protección para rutas de admin
                                .requestMatchers("/productos/**").hasAuthority("ADMIN")  // Autenticación para otras rutas
                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                )
                .formLogin(
                        formLogin -> formLogin
                        .loginPage("/usuario/login")                           // Página de inicio de sesión personalizada
                        .permitAll()                                           // Permitir acceso a todos
                        .defaultSuccessUrl("/usuario/acceder", true)         // Redirigir a esta URL tras login exitoso
                );
        return http.build();
    }
    //maneja el tipo de password encoder
    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

}
