package backend.dto;

import backend.entity.CategorieChatbot;
import backend.entity.TypeReponseChatbot;

public class ChatbotQuestionDto {

    private Integer questionId;
    private CategorieChatbot categorie;
    private String question;
    private TypeReponseChatbot typeReponse;

    public ChatbotQuestionDto() {
    }

    public ChatbotQuestionDto(Integer questionId, CategorieChatbot categorie, String question, TypeReponseChatbot typeReponse) {
        this.questionId = questionId;
        this.categorie = categorie;
        this.question = question;
        this.typeReponse = typeReponse;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public CategorieChatbot getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieChatbot categorie) {
        this.categorie = categorie;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public TypeReponseChatbot getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(TypeReponseChatbot typeReponse) {
        this.typeReponse = typeReponse;
    }
}