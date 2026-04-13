package backend.service;

import backend.dao.AppUtilisateurRepository;
import backend.entity.AppUtilisateur;
import backend.exception.ResourceNotFoundException;
import backend.exception.UnauthorizedActionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelperService {

    private final AppUtilisateurRepository appUtilisateurRepository;

    public SecurityHelperService(AppUtilisateurRepository appUtilisateurRepository) {
        this.appUtilisateurRepository = appUtilisateurRepository;
    }

    public AppUtilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UnauthorizedActionException("Utilisateur non authentifié");
        }

        String email = authentication.getName();
        return appUtilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
    }

    public void checkPatientAccess(Long patientId) {
        AppUtilisateur currentUser = getCurrentUser();

        if (currentUser.getRole().name().equals("PATIENT") && !currentUser.getId().equals(patientId)) {
            throw new UnauthorizedActionException("Accès interdit à ce patient");
        }
    }
}