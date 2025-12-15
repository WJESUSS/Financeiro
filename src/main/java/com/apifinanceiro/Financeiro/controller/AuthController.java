package com.apifinanceiro.Financeiro.controller;


import com.apifinanceiro.Financeiro.domain.User;
import com.apifinanceiro.Financeiro.service.AuthService;
import com.apifinanceiro.Financeiro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            // Captura a exceção lançada pelo UserService
            if (e.getMessage() != null && e.getMessage().contains("Nome de usuário já existe")) {
                // Retorna 409 Conflict (ou 400 Bad Request)
                String message = "Erro ao registrar: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }
            // Retorna um erro interno para outras exceções não tratadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao registrar usuário.");
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword());
    }
}
