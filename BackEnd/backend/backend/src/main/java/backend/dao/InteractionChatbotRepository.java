package backend.dao;

import backend.entity.InteractionChatbot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.List;

public interface InteractionChatbotRepository extends JpaRepository<InteractionChatbot, Long> {
    List<InteractionChatbot> findByDossierPatientId(Long dossierId);
    
@Query("SELECT MAX(i.dateInteraction) FROM InteractionChatbot i WHERE i.dossierPatient.id = :dossierId AND i.questionId = :questionId")
Optional<LocalDateTime> findLastAnswerDateByDossierAndQuestionId(@Param("dossierId") Long dossierId, @Param("questionId") Integer questionId);
}