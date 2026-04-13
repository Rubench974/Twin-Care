package backend.dto;

public class ResetPasswordRequest {
    private String email;
    private String nouveauMotDePasse;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNouveauMotDePasse() { return nouveauMotDePasse; }
    public void setNouveauMotDePasse(String nouveauMotDePasse) { this.nouveauMotDePasse = nouveauMotDePasse; }
}