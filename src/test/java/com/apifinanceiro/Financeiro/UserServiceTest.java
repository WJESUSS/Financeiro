package com.apifinanceiro.Financeiro;




import com.apifinanceiro.Financeiro.domain.User;
import com.apifinanceiro.Financeiro.repository.UserRepository;
import com.apifinanceiro.Financeiro.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    void testRegisterUser() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        UserService service = new UserService(repo, encoder);

        User user = new User();
        user.setUsername("wash");
        user.setPassword("1234");

        when(encoder.encode("1234")).thenReturn("hashed");
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        User saved = service.register(user);

        assertEquals("hashed", saved.getPassword());
        assertEquals("ROLE_USER", saved.getRole());
    }
}

