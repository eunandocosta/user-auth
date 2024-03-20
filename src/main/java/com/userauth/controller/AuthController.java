package com.userauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.userauth.models.user.AuthenticationDTO;
import com.userauth.models.user.LoginResponseDTO;
import com.userauth.models.user.RegisterDTO;
import com.userauth.models.user.User;
import com.userauth.models.user.UserDTO;
import com.userauth.repository.UserRepository;
import com.userauth.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins="*")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid AuthenticationDTO data){
        try {
            var UserPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(UserPassword);
            System.out.println("Logando");
            var token = tokenService.generateToken((User) auth.getPrincipal());

            LoginResponseDTO loginResponse = new LoginResponseDTO(token);

            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha em autenticar");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterDTO data) {
        if(this.userRepository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já está sendo utilizado");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), data.dateOfBirth(), data.image(), encryptedPassword );
        System.out.println(newUser);
        this.userRepository.save(newUser);
        return ResponseEntity.ok().body("Registrado com sucesso");
    }

    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@RequestParam String email){
        System.out.println("retrieving data");
        try{
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
            UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), user.getDateOfBirth(), user.getImage());
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
