package backend.dao;

import backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // Tri par priorité : HAUTE, MOYENNE, FAIBLE (ordre naturel de l'enum)
    List<Question> findAllByOrderByPrioriteAsc();
}