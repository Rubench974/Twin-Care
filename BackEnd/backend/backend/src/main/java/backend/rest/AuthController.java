package backend.rest;

import backend.dto.LoginRequest;
import backend.dto.RegisterRequest;
import backend.entity.AppUtilisateur;
import backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AppUtilisateur register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AppUtilisateur login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}