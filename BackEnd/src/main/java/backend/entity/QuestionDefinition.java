package backend.entity;

public class QuestionDefinition {

    private Integer id;
    private CategorieChatbot categorie;
    private String question;
    private TypeReponseChatbot typeReponse;
    private String declencheur;

    public QuestionDefinition() {
    }

    public QuestionDefinition(Integer id, CategorieChatbot categorie, String question,
            TypeReponseChatbot typeReponse, String declencheur) {
        this.id = id;
        this.categorie = categorie;
        this.question = question;
        this.typeReponse = typeReponse;
        this.declencheur = declencheur;
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
}