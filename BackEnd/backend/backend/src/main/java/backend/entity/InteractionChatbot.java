package backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interaction_chatbot")
public class InteractionChatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateInteraction;

    private Integer questionId;

    @Column(nullable = false, length = 500)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private CategorieChatbot categorie;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private TypeReponseChatbot typeReponse;

    @Column(length = 255)
    private String reponseTexte;

    private Integer reponseNumerique;

    private boolean declaratifPatient;

    private boolean aRevoirParProfessionnel;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private AppUtilisateur patient;

    @ManyToOne
    @JoinColumn(name = "dossier_patient_id", nullable = false)
    private DossierPatient dossierPatient;

    public InteractionChatbot() {
        this.dateInteraction = LocalDateTime.now();
        this.declaratifPatient = true;
        this.aRevoirParProfessionnel = true;
    }

    public Long getId() { return id; }
    public LocalDateTime getDateInteraction() { return dateInteraction; }
    public Integer getQuestionId() { return questionId; }
    public String getQuestion() { return question; }
    public CategorieChatbot getCategorie() { return categorie; }
    public TypeReponseChatbot getTypeReponse() { return typeReponse; }
    public String getReponseTexte() { return reponseTexte; }
    public Integer getReponseNumerique() { return reponseNumerique; }
    public boolean isDeclaratifPatient() { return declaratifPatient; }
    public boolean isARevoirParProfessionnel() { return aRevoirParProfessionnel; }
    public AppUtilisateur getPatient() { return patient; }
    public DossierPatient getDossierPatient() { return dossierPatient; }

    public void setDateInteraction(LocalDateTime dateInteraction) { this.dateInteraction = dateInteraction; }
    public void setQuestionId(Integer questionId) { this.questionId = questionId; }
    public void setQuestion(String question) { this.question = question; }
    public void setCategorie(CategorieChatbot categorie) { this.categorie = categorie; }
    public void setTypeReponse(TypeReponseChatbot typeReponse) { this.typeReponse = typeReponse; }
    public void setReponseTexte(String reponseTexte) { this.reponseTexte = reponseTexte; }
    public void setReponseNumerique(Integer reponseNumerique) { this.reponseNumerique = reponseNumerique; }
    public void setDeclaratifPatient(boolean declaratifPatient) { this.declaratifPatient = declaratifPatient; }
    public void setARevoirParProfessionnel(boolean aRevoirParProfessionnel) { this.aRevoirParProfessionnel = aRevoirParProfessionnel; }
    public void setPatient(AppUtilisateur patient) { this.patient = patient; }
    public void setDossierPatient(DossierPatient dossierPatient) { this.dossierPatient = dossierPatient; }
}