package backend.dao;

import backend.entity.AppUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUtilisateurRepository extends JpaRepository<AppUtilisateur, Long> {
    Optional<AppUtilisateur> findByEmail(String email);
}