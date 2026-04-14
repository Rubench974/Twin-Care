package backend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dossier_patient")
public class DossierPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private StatutDossier statut;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private AppUtilisateur patient;

    @OneToMany(mappedBy = "dossierPatient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "dossierPatient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InteractionChatbot> interactions = new ArrayList<>();

    public DossierPatient() {
        this.dateCreation = LocalDateTime.now();
        this.statut = StatutDossier.EN_COURS;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutDossier getStatut() {
        return statut;
    }

    public void setStatut(StatutDossier statut) {
        this.statut = statut;
    }

    public AppUtilisateur getPatient() {
        return patient;
    }

    public void setPatient(AppUtilisateur patient) {
        this.patient = patient;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<InteractionChatbot> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<InteractionChatbot> interactions) {
        this.interactions = interactions;
    }
}