package backend.service;

import backend.dto.*;
import backend.entity.*;
import backend.dao.*;
import backend.exception.BadRequestException;
import backend.exception.ResourceNotFoundException;
import backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

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

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (req.getEmail() == null || req.getEmail().isBlank())
            throw new BadRequestException("Email obligatoire");
        if (req.getMotDePasse() == null || req.getMotDePasse().isBlank())
            throw new BadRequestException("Mot de passe obligatoire");
        if (req.getRole() == null)
            throw new BadRequestException("Rôle obligatoire");
        if (repo.findByEmail(req.getEmail()).isPresent())
            throw new BadRequestException("Email déjà utilisé");

        AppUtilisateur user = new AppUtilisateur(
                req.getNom(),
                req.getPrenom(),
                req.getDateNaissance(),
                req.getEmail(),
                encoder.encode(req.getMotDePasse()),
                req.getRole(),
                req.getSexe()
        );

        AppUtilisateur saved = repo.save(user);
        log.info("Inscription utilisateur : {}", saved.getEmail());

        if (saved.getRole() == Role.PATIENT) {
            DossierPatient d = new DossierPatient();
            d.setPatient(saved);
            dossierRepo.save(d);
            saved.setDossierPatient(d);
        }

        var userDetails = userDetailsService.loadUserByUsername(saved.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, saved.getEmail(), saved.getRole().name());
    }

    public AuthResponse login(LoginRequest req) {
        AppUtilisateur user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        if (!encoder.matches(req.getMotDePasse(), user.getMotDePasse()))
            throw new BadRequestException("Mot de passe incorrect");

        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        log.info("Connexion utilisateur : {}", user.getEmail());
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}