package backend.service;

import backend.entity.AppUtilisateur;
import backend.exception.ResourceNotFoundException;
import backend.dao.AppUtilisateurRepository;
import backend.security.UtilisateurSecurite;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUtilisateurRepository repository;

    public CustomUserDetailsService(AppUtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        AppUtilisateur user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        return new UtilisateurSecurite(
                user.getEmail(),
                user.getMotDePasse(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}