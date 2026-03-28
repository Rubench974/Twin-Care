package backend.service;

import backend.dto.*;
import backend.entity.*;
import backend.dao.*;
import backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUtilisateurRepository repo;
    private final DossierPatientRepository dossierRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(AppUtilisateurRepository repo,
                       DossierPatientRepository dossierRepo,
                       PasswordEncoder encoder,
                       JwtService jwtService,
                       CustomUserDetailsService userDetailsService) {
        this.repo = repo;
        this.dossierRepo = dossierRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public AppUtilisateur register(RegisterRequest req) {
        AppUtilisateur user = new AppUtilisateur(
                req.getNom(),
                req.getPrenom(),
                req.getEmail(),
                encoder.encode(req.getMotDePasse()),
                req.getRole()
        );

        AppUtilisateur saved = repo.save(user);

        if (saved.getRole() == Role.PATIENT) {
            DossierPatient d = new DossierPatient();
            d.setPatient(saved);
            dossierRepo.save(d);
        }

        return saved;
    }

    public AuthResponse login(LoginRequest req) {
        AppUtilisateur user = repo.findByEmail(req.getEmail())
                .orElseThrow();

        if (!encoder.matches(req.getMotDePasse(), user.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}