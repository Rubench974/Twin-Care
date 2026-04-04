package backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "validation")
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateValidation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DecisionValidation decision;

    @Column(length = 1000)
    private String commentaire;

    @OneToOne
    @JoinColumn(name = "document_id", nullable = false, unique = true)
    private Document document;

    @ManyToOne
    @JoinColumn(name = "assistant_medical_id", nullable = false)
    private AppUtilisateur assistantMedical;

    public Validation() {
        this.dateValidation = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public DecisionValidation getDecision() {
        return decision;
    }

    public void setDecision(DecisionValidation decision) {
        this.decision = decision;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public AppUtilisateur getAssistantMedical() {
        return assistantMedical;
    }

    public void setAssistantMedical(AppUtilisateur assistantMedical) {
        this.assistantMedical = assistantMedical;
    }
}