package com.userauth.models.user;

import java.time.LocalDate;

public record RegisterDTO(String name, String email, String password, LocalDate dateOfBirth, byte[] image) {
    
}
