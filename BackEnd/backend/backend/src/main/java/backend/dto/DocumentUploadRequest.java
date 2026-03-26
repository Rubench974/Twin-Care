package backend.dto;

import backend.entity.TypeDocument;
import java.time.LocalDate;

public class DocumentUploadRequest {

    private TypeDocument type;
    private LocalDate dateDocument;
    private String prescripteur;
    private String commentairePatient;

    public TypeDocument getType() {
        return type;
    }

    public void setType(TypeDocument type) {
        this.type = type;
    }

    public LocalDate getDateDocument() {
        return dateDocument;
    }

    public void setDateDocument(LocalDate dateDocument) {
        this.dateDocument = dateDocument;
    }

    public String getPrescripteur() {
        return prescripteur;
    }

    public void setPrescripteur(String prescripteur) {
        this.prescripteur = prescripteur;
    }

    public String getCommentairePatient() {
        return commentairePatient;
    }

    public void setCommentairePatient(String commentairePatient) {
        this.commentairePatient = commentairePatient;
    }
}