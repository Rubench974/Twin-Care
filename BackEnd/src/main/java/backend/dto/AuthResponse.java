package backend.dto;

public class AuthResponse {

    private String token;
    private String email;
    private String role;
    private Long patientId;
    private Long dossierId;
    private String prenom;
    private String nom;

    public AuthResponse() {
    }

    public AuthResponse(String token, String email, String role, Long patientId, Long dossierId, String prenom, String nom) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.patientId = patientId;
        this.dossierId = dossierId;
        this.prenom = prenom;
        this.nom = nom;
    }

    public AuthResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getPatientId() { 
        return patientId; 
    } 

    public void setPatientId(Long patientId) {
        this.patientId = patientId; 
    } 

    public Long getDossierId() {
        return dossierId;
    } 

    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId; 
    } 

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}