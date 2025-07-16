package com.cespi.capacitacion.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println("Hola desde Auth");
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/prueba")
    public ResponseEntity<Map<String, String>> prueba() {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Prueba de API");
        return ResponseEntity.ok(response);
    }

}
