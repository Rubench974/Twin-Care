package backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500)
    private String texte;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategorieChatbot categorie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeReponseChatbot typeReponse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priorite priorite;

    @Column(length = 255)
    private String declencheur;  // déclencheur métier (ex: "Age > 65 ans")

    public Question() {}

    public Question(String texte, CategorieChatbot categorie, TypeReponseChatbot typeReponse, Priorite priorite, String declencheur) {
        this.texte = texte;
        this.categorie = categorie;
        this.typeReponse = typeReponse;
        this.priorite = priorite;
        this.declencheur = declencheur;
    }

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTexte() { return texte; }
    public void setTexte(String texte) { this.texte = texte; }
    public CategorieChatbot getCategorie() { return categorie; }
    public void setCategorie(CategorieChatbot categorie) { this.categorie = categorie; }
    public TypeReponseChatbot getTypeReponse() { return typeReponse; }
    public void setTypeReponse(TypeReponseChatbot typeReponse) { this.typeReponse = typeReponse; }
    public Priorite getPriorite() { return priorite; }
    public void setPriorite(Priorite priorite) { this.priorite = priorite; }
    public String getDeclencheur() { return declencheur; }
    public void setDeclencheur(String declencheur) { this.declencheur = declencheur; }
}