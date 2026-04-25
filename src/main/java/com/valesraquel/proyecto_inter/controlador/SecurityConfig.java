package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Configuración de seguridad de la aplicación con Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Usamos NoOpPasswordEncoder porque las contraseñas están en texto plano en la BD
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Define qué rutas son accesibles para cada rol
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/alumno/**").hasAuthority("ALUMNO")
                        .requestMatchers("/tutorempresa/**").hasAuthority("TUTOR_EMPRESA")
                        .requestMatchers("/tutorcentro/**").hasAuthority("TUTOR_CENTRO")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // Configura el AuthenticationManager para que use nuestro servicio de usuarios
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(usuarioServicio)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}