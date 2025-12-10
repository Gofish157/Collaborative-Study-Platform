package com.studyplatform.server.config;

import com.studyplatform.server.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

                .cors(cors -> {})

                .csrf(csrf -> csrf.disable())


                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers("/api/users/me").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/groups/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/groups").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/groups/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/groups/*").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/groups/*/tasks/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/groups/*/tasks").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/groups/*/tasks/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/groups/*/tasks/*").authenticated()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
