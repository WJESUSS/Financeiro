package com.apifinanceiro.Financeiro.controller;

import com.apifinanceiro.Financeiro.domain.User;
import com.apifinanceiro.Financeiro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retorna todos os usuários (apenas admins)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listAll() {
        return userService.findAll();
    }

    /**
     * Retorna o perfil do usuário logado
     */
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    /**
     * Atualiza os dados do usuário logado
     */
    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestBody User updatedUser) {
        return userService.updateUser(userDetails.getUsername(), updatedUser);
    }

    /**
     * Busca usuário por ID (apenas admins)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getById(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}

