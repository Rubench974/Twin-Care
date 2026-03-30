package backend.dto;

import backend.entity.CategorieChatbot;
import backend.entity.TypeReponseChatbot;

public class ChatbotAnswerRequest {

    private Integer questionId;
    private String question;
    private CategorieChatbot categorie;
    private TypeReponseChatbot typeReponse;
    private String reponseTexte;
    private Integer reponseNumerique;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public CategorieChatbot getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieChatbot categorie) {
        this.categorie = categorie;
    }

    public TypeReponseChatbot getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(TypeReponseChatbot typeReponse) {
        this.typeReponse = typeReponse;
    }

    public String getReponseTexte() {
        return reponseTexte;
    }

    public void setReponseTexte(String reponseTexte) {
        this.reponseTexte = reponseTexte;
    }

    public Integer getReponseNumerique() {
        return reponseNumerique;
    }

    public void setReponseNumerique(Integer reponseNumerique) {
        this.reponseNumerique = reponseNumerique;
    }
}