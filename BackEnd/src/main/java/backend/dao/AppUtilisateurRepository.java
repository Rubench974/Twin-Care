package backend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.AppUtilisateur;

public interface AppUtilisateurRepository extends JpaRepository<AppUtilisateur, Long> {
    Optional<AppUtilisateur> findByEmail(String email);
}