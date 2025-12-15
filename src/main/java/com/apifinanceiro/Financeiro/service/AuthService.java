package com.apifinanceiro.Financeiro.service;

import com.apifinanceiro.Financeiro.domain.User;
import com.apifinanceiro.Financeiro.security.JwtUtil;
// import lombok.RequiredArgsConstructor; // ⬅️ REMOVIDO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
// ⬅️ REMOVIDO @RequiredArgsConstructor para usar apenas o construtor customizado
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // 🚨 CORREÇÃO: Apenas um construtor explícito com @Autowired e @Lazy
    @Autowired
    public AuthService(@Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public String login(String username, String password) {
        // 1. Autentica o usuário
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // 2. 🚨 CORREÇÃO: Usa loadUserByUsername() para obter o UserDetails,
        // e faz o CAST para a sua entidade 'User' para o JwtUtil.
        // O método findByUsername não é mais necessário aqui se loadUserByUsername retorna User.
        User user = (User) userService.loadUserByUsername(username);

        // 3. Gera e retorna o token
        return jwtUtil.generateToken(user);
    }
}