package com.project.movieapi.controller;

import com.project.movieapi.model.User;
import com.project.movieapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:8080"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Cadastro de usuário
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        System.out.println("=== RECEBENDO USUÁRIO ===");
        System.out.println("Nome: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Senha: " + user.getPassword());
        System.out.println("==========================");

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // Login de usuário
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        System.out.println("=== TENTATIVA DE LOGIN ===");
        System.out.println("Email: " + loginUser.getEmail());
        System.out.println("Senha: " + loginUser.getPassword());
        System.out.println("===========================");

        Optional<User> user = userRepository.findByEmail(loginUser.getEmail());

        if (user.isPresent()) {
            if (user.get().getPassword().equals(loginUser.getPassword())) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(401).body("Senha incorreta.");
            }
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }
}
