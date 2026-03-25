package backend.dto;

import backend.entity.DecisionValidation;

public class ValidationRequest {
    private DecisionValidation decision;
    private String commentaire;
    private Long assistantMedicalId;

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

    public Long getAssistantMedicalId() {
        return assistantMedicalId;
    }

    public void setAssistantMedicalId(Long assistantMedicalId) {
        this.assistantMedicalId = assistantMedicalId;
    }
}