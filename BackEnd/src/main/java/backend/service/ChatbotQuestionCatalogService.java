package backend.service;

import backend.dao.QuestionRepository;
import backend.entity.Question;
import backend.entity.QuestionDefinition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatbotQuestionCatalogService {

    private final QuestionRepository questionRepository;

    public ChatbotQuestionCatalogService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDefinition> getAllQuestions() {
        List<Question> questions = questionRepository.findAllByOrderByPrioriteAsc();
        return questions.stream()
                .map(q -> new QuestionDefinition(
                        q.getId(),
                        q.getCategorie(),
                        q.getTexte(),
                        q.getTypeReponse(),
                        q.getDeclencheur(),
                        q.getPriorite()
                ))
                .collect(Collectors.toList());
    }
}