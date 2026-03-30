package backend.dto;

import backend.entity.Role;
import backend.entity.Sexe;
import java.time.LocalDate;

public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private Role role;
    private LocalDate dateNaissance;
    private Sexe sexe;

     public RegisterRequest() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public Sexe getSexe() {
        return sexe;
    }   
    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }
}