package backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interaction_chatbot")
public class InteractionChatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateInteraction;

    @Column(nullable = false, length = 500)
    private String question;

    @Column(length = 2000)
    private String reponse;

    @Column(length = 100)
    private String categorie;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private AppUtilisateur patient;

    @ManyToOne
    @JoinColumn(name = "dossier_patient_id", nullable = false)
    private DossierPatient dossierPatient;

    public InteractionChatbot() {
        this.dateInteraction = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateInteraction() {
        return dateInteraction;
    }

    public void setDateInteraction(LocalDateTime dateInteraction) {
        this.dateInteraction = dateInteraction;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public AppUtilisateur getPatient() {
        return patient;
    }

    public void setPatient(AppUtilisateur patient) {
        this.patient = patient;
    }

    public DossierPatient getDossierPatient() {
        return dossierPatient;
    }

    public void setDossierPatient(DossierPatient dossierPatient) {
        this.dossierPatient = dossierPatient;
    }
}