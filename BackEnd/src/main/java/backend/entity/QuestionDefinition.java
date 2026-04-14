package backend.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class QuestionDefinition {

    private Integer id;
    @Enumerated(EnumType.STRING)
    private CategorieChatbot categorie;
    private String question;
    @Enumerated(EnumType.STRING)
    private TypeReponseChatbot typeReponse;
    private String declencheur;
    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    public QuestionDefinition() {
    }

    public QuestionDefinition(Integer id, CategorieChatbot categorie, String question,
            TypeReponseChatbot typeReponse, String declencheur, Priorite priorite) {
        this.id = id;
        this.categorie = categorie;
        this.question = question;
        this.typeReponse = typeReponse;
        this.declencheur = declencheur;
        this.priorite = priorite;
    }

    public Integer getId() {
        return id;
    }

    public CategorieChatbot getCategorie() {
        return categorie;
    }

    public String getQuestion() {
        return question;
    }

    public TypeReponseChatbot getTypeReponse() {
        return typeReponse;
    }

    public String getDeclencheur() {
        return declencheur;
    }
    public Priorite getPriorite() {
        return priorite;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategorie(CategorieChatbot categorie) {
        this.categorie = categorie;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTypeReponse(TypeReponseChatbot typeReponse) {
        this.typeReponse = typeReponse;
    }

    public void setDeclencheur(String declencheur) {
        this.declencheur = declencheur;
    }
    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }
}