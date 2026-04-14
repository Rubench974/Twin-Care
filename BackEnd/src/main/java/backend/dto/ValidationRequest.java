package backend.dto;

import backend.entity.DecisionValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ValidationRequest {

    @NotNull
    private DecisionValidation decision;

    @Size(max = 1000)
    private String commentaire;

    @NotNull
    private Long assistantMedicalId;

    public DecisionValidation getDecision() { return decision; }
    public void setDecision(DecisionValidation decision) { this.decision = decision; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public Long getAssistantMedicalId() { return assistantMedicalId; }
    public void setAssistantMedicalId(Long assistantMedicalId) { this.assistantMedicalId = assistantMedicalId; }
}