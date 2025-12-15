package com.apifinanceiro.Financeiro.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority; // Importe
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importe
import org.springframework.security.core.userdetails.UserDetails; // ⬅️ IMPORTANTE
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 🚨 CORREÇÃO: Implementa UserDetails
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // ROLE_USER, ROLE_ADMIN

    // ----------------------------------------------------
    // MÉTODOS DE USERDETAILS OBRIGATÓRIOS
    // ----------------------------------------------------

    /**
     * Retorna a coleção de autoridades (papéis/roles) concedidas ao usuário.
     * @return Uma coleção de SimpleGrantedAuthority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte a String 'role' (ex: "ROLE_USER") em uma coleção de GrantedAuthority
        return List.of(new SimpleGrantedAuthority(role));
    }

    /**
     * Retorna a senha usada para autenticar o usuário.
     * Já é o seu getter existente.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Retorna o nome de usuário (identificador).
     * Já é o seu getter existente.
     */
    @Override
    public String getUsername() {
        return username;
    }

    // --- Métodos de Status da Conta (Para controle de segurança) ---

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira (padrão)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta não bloqueada (padrão)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciais (senha) não expiradas (padrão)
    }

    @Override
    public boolean isEnabled() {
        return true; // Usuário habilitado (padrão)
    }

    // ----------------------------------------------------
    // MÉTODOS LOMBOK/GETTERS ANTERIORES (Mantidos)
    // ----------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Os outros getters/setters são sobrescritos pelo @Data do Lombok.
    // Manter a sua versão manual é opcional, mas vamos focar na solução.
    // Os métodos getUsername() e getPassword() foram explicitamente sobrescritos acima.

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}