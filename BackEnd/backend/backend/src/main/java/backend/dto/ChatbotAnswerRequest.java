package backend.dto;

import backend.entity.CategorieChatbot;

public class ChatbotAnswerRequest {

    private Integer questionId;
    private String question;
    private CategorieChatbot categorie;
    private String typeReponse;
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

    public String getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(String typeReponse) {
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