package backend.dao;

import backend.entity.InteractionChatbot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteractionChatbotRepository extends JpaRepository<InteractionChatbot, Long> {
    List<InteractionChatbot> findByDossierPatientId(Long dossierId);
}