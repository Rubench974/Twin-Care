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

    private final AppUtilisateurRepository appUtilisateurRepository;
    private final DossierPatientRepository dossierPatientRepository;

    public AuthService(AppUtilisateurRepository appUtilisateurRepository,
                       DossierPatientRepository dossierPatientRepository) {
        this.appUtilisateurRepository = appUtilisateurRepository;
        this.dossierPatientRepository = dossierPatientRepository;
    }

    public AppUtilisateur register(RegisterRequest request) {
        if (appUtilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        AppUtilisateur Utilisateur = new AppUtilisateur(
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getMotDePasse(),
                request.getRole()
        );

        AppUtilisateur savedUtilisateur = appUtilisateurRepository.save(Utilisateur);

        if (savedUtilisateur.getRole() == Role.PATIENT) {
            DossierPatient dossier = new DossierPatient();
            dossier.setPatient(savedUtilisateur);
            dossierPatientRepository.save(dossier);
        }

        return savedUtilisateur;
    }

    public AppUtilisateur login(LoginRequest request) {
        AppUtilisateur Utilisateur = appUtilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!Utilisateur.getMotDePasse().equals(request.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return Utilisateur;
    }
}