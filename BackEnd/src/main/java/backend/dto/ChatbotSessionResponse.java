package backend.dto;

import java.util.List;

public class ChatbotSessionResponse {

    private Long patientId;
    private Long dossierId;
    private List<ChatbotQuestionDto> questions;

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

    public List<ChatbotQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ChatbotQuestionDto> questions) {
        this.questions = questions;
    }
}