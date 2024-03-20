package com.userauth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.userauth.models.user.User;
import com.userauth.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthService authService;

    @Test
    void testLoadUserByUsername_whenExists_returnUser() {
        // Preparação
        String email = "test@example.com";
        byte[] image = new byte[] { 1, 2, 3 }; // Exemplo de dados de imagem
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1); // Exemplo de data de nascimento

        User mockUser = new User();
        mockUser.setName("Teste");
        mockUser.setEmail(email);
        mockUser.setPassword("passwordtest");
        mockUser.setImage(image);
        mockUser.setDateOfBirth(dateOfBirth);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Ação
        UserDetails result = authService.loadUserByUsername(email);

        // Verificação
        assertEquals(email, result.getUsername());
        
        // Para converter UserDetails de volta para User (se necessário e possível, dependendo de sua implementação)
        User userDetailsAsUser = (User) result;
        assertEquals(mockUser.getEmail(), userDetailsAsUser.getEmail());
        assertEquals(mockUser.getDateOfBirth(), userDetailsAsUser.getDateOfBirth());
        assertEquals(mockUser.getImage(), userDetailsAsUser.getImage());

        verify(userRepository).findByEmail(email);
    }

    @Test
    void testLoadUserByUsername_whenNotExists_throwsUserNotFoundException() {
        // Configuração
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername(email);
        });

        verify(userRepository).findByEmail(email);
    }
}
