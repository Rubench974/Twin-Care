package backend.service;

import backend.dto.LoginRequest;
import backend.dto.RegisterRequest;
import backend.entity.AppUtilisateur;
import backend.entity.DossierPatient;
import backend.entity.Role;
import backend.dao.AppUtilisateurRepository;
import backend.dao.DossierPatientRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUtilisateurRepository AppUtilisateurRepository;
    private final DossierPatientRepository dossierPatientRepository;

    public AuthService(AppUtilisateurRepository AppUtilisateurRepository,
                       DossierPatientRepository dossierPatientRepository) {
        this.AppUtilisateurRepository = AppUtilisateurRepository;
        this.dossierPatientRepository = dossierPatientRepository;
    }

    public AppUtilisateur register(RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("Email obligatoire");
        }

        if (request.getMotDePasse() == null || request.getMotDePasse().isBlank()) {
            throw new RuntimeException("Mot de passe obligatoire");
        }

        if (request.getRole() == null) {
            throw new RuntimeException("Rôle obligatoire");
        }

        if (AppUtilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        AppUtilisateur user = new AppUtilisateur(
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getMotDePasse(),
                request.getRole()
        );

        AppUtilisateur savedUser = AppUtilisateurRepository.save(user);

        if (savedUser.getRole() == Role.PATIENT) {
            DossierPatient dossier = new DossierPatient();
            dossier.setPatient(savedUser);
            dossierPatientRepository.save(dossier);
        }

        return savedUser;
    }

    public AppUtilisateur login(LoginRequest request) {
        AppUtilisateur user = AppUtilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!user.getMotDePasse().equals(request.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return user;
    }
}