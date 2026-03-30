package backend.entity;

import java.time.LocalDate;

public class PatientProfile {

    private Long patientId;
    private Integer age;
    private Sexe sexe;
    private LocalDate dateDerniereConnexion;
    private boolean dossierIncomplet;
    private String saison;
    private boolean allergiesConnues;
    private Integer sommeilScore;
    private Integer stressScore;
    private Integer activitePhysiqueJours;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateDerniereConnexion() {
        return dateDerniereConnexion;
    }

    public void setDateDerniereConnexion(LocalDate dateDerniereConnexion) {
        this.dateDerniereConnexion = dateDerniereConnexion;
    }

    public boolean isDossierIncomplet() {
        return dossierIncomplet;
    }

    public void setDossierIncomplet(boolean dossierIncomplet) {
        this.dossierIncomplet = dossierIncomplet;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public boolean isAllergiesConnues() {
        return allergiesConnues;
    }

    public void setAllergiesConnues(boolean allergiesConnues) {
        this.allergiesConnues = allergiesConnues;
    }

    public Integer getSommeilScore() {
        return sommeilScore;
    }

    public void setSommeilScore(Integer sommeilScore) {
        this.sommeilScore = sommeilScore;
    }

    public Integer getStressScore() {
        return stressScore;
    }

    public void setStressScore(Integer stressScore) {
        this.stressScore = stressScore;
    }

    public Integer getActivitePhysiqueJours() {
        return activitePhysiqueJours;
    }

    public void setActivitePhysiqueJours(Integer activitePhysiqueJours) {
        this.activitePhysiqueJours = activitePhysiqueJours;
    }
}