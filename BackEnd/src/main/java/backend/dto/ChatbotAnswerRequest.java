package backend.dto;

import backend.entity.CategorieChatbot;
import backend.entity.TypeReponseChatbot;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChatbotAnswerRequest {

    @NotNull
    private Integer questionId;

    @NotNull
    @Size(min = 1, max = 500)
    private String question;

    @NotNull
    private CategorieChatbot categorie;

    @NotNull
    private TypeReponseChatbot typeReponse;

    @Size(max = 255)
    private String reponseTexte;

    private Integer reponseNumerique;

    public Integer getQuestionId() { return questionId; }
    public void setQuestionId(Integer questionId) { this.questionId = questionId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public CategorieChatbot getCategorie() { return categorie; }
    public void setCategorie(CategorieChatbot categorie) { this.categorie = categorie; }

    public TypeReponseChatbot getTypeReponse() { return typeReponse; }
    public void setTypeReponse(TypeReponseChatbot typeReponse) { this.typeReponse = typeReponse; }

    public String getReponseTexte() { return reponseTexte; }
    public void setReponseTexte(String reponseTexte) { this.reponseTexte = reponseTexte; }

    public Integer getReponseNumerique() { return reponseNumerique; }
    public void setReponseNumerique(Integer reponseNumerique) { this.reponseNumerique = reponseNumerique; }
}