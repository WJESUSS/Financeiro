package com.apifinanceiro.Financeiro.service;

import com.apifinanceiro.Financeiro.domain.User;
import com.apifinanceiro.Financeiro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // ⬅️ IMPORTADO
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// 🚨 CORREÇÃO: Implementa a interface UserDetailsService
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        try {
            // Tenta salvar o usuário
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // 🚨 TRATAMENTO: Captura a exceção de duplicidade e lança uma exceção customizada
            // ou uma RuntimeException simples para ser capturada pelo Controller.
            if (e.getMessage() != null && e.getMessage().contains("duplicate key")) {
                throw new RuntimeException("Nome de usuário já existe: " + user.getUsername(), e);
            }
            throw e; // Lança outras exceções de integridade
        }
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(String username, User updatedUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }

        return userRepository.save(user);
    }

    // 🚨 CORREÇÃO: @Override do método da interface
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // Retorna o objeto User, assumindo que ele implementa UserDetails
        // Se a sua classe User não implementa UserDetails, mude o return para a linha abaixo:
        /*
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
        */
        return user;
    }
}