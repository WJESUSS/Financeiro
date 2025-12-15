package com.apifinanceiro.Financeiro.security;

import com.apifinanceiro.Financeiro.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
// Removendo @RequiredArgsConstructor, vamos usar injeção por método
public class SecurityConfig {

    // Remover campos private final JwtUtil jwtUtil; e private final UserService userService;

    @Bean
    // Recebe as dependências como parâmetro, não por injeção de campo
    public JwtFilter jwtFilter(JwtUtil jwtUtil, UserService userService) {
        return new JwtFilter(jwtUtil, userService);
    }

    @Bean
    // Recebe o filtro como parâmetro
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Adiciona o filtro customizado
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Este é o método que dispara a recursão se as dependências do UserService estiverem injetadas acima
        return config.getAuthenticationManager();
    }
}