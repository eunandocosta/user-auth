package com.userauth.models.user;

import java.time.LocalDate;

public record UserDTO(String name, String email, LocalDate dateOfBirth, byte[] image){
    
}
