package backend.dto;

public class AuthResponse {

    private String token;
    private String email;
    private String role;
    private Long patientId;
    private Long dossierId;
    

    public AuthResponse() {
    }

    public AuthResponse(String token, String email, String role, Long patientId, Long dossierId) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.patientId = patientId;
        this.dossierId = dossierId;
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
}