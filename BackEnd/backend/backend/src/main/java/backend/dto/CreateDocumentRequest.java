package backend.dto;

import backend.entity.TypeDocument;

import java.time.LocalDate;

public class CreateDocumentRequest {
    private TypeDocument type;
    private LocalDate dateDocument;
    private String nomFichier;
    private String cheminFichier;
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

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
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