package org.example.springbootvictor.JWT;

import org.example.springbootvictor.JWT.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Route d'authentification
                        .requestMatchers("/api/auth/**").permitAll()

                        // Pour les JV
                        .requestMatchers(HttpMethod.GET, "/api/videogames/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/videogames/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/videogames/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/videogames/**").hasRole("ADMIN")

                        // Pour les reviews
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/reviews/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                        // Pour les categories
                        .requestMatchers(HttpMethod.GET, "/api/category/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/category/**").hasRole("ADMIN")


                        // Pour autoriser l'accès aux swaggers
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Pas de sessions
                );

        // Pour mettre JWT en avant (auto généré)
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // pour hasher les mdp
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}