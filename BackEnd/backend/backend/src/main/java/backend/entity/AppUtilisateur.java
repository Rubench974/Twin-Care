package backend.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_utilisateur")
public class AppUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private DossierPatient dossierPatient;

    @OneToMany(mappedBy = "patient")
    private List<InteractionChatbot> interactionsChatbot = new ArrayList<>();

    @OneToMany(mappedBy = "assistantMedical")
    private List<Validation> validationsRealisees = new ArrayList<>();

    public AppUtilisateur() {
    }

    public AppUtilisateur(String nom, String prenom, String email, String motDePasse, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public Long getId() {
        return id;
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

    public DossierPatient getDossierPatient() {
        return dossierPatient;
    }

    public void setDossierPatient(DossierPatient dossierPatient) {
        this.dossierPatient = dossierPatient;
    }

    public List<InteractionChatbot> getInteractionsChatbot() {
        return interactionsChatbot;
    }

    public void setInteractionsChatbot(List<InteractionChatbot> interactionsChatbot) {
        this.interactionsChatbot = interactionsChatbot;
    }

    public List<Validation> getValidationsRealisees() {
        return validationsRealisees;
    }

    public void setValidationsRealisees(List<Validation> validationsRealisees) {
        this.validationsRealisees = validationsRealisees;
    }
}