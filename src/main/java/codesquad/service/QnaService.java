package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.ForbiddenRequestException;
import codesquad.domain.*;
import codesquad.dto.QuestionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("qnaService")
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    @Resource(name = "questionRepository")
    private QuestionRepository questionRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Question create(User loginUser, QuestionDto questionDto) {
        Question question = questionDto.toQuestion();
        question.writeBy(loginUser);
        return questionRepository.save(question);
    }

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    public QuestionDto update(User loginUser, Long id, QuestionDto updatedQuestionDto) throws DataAccessException {
        Optional<Question> maybeQuestion = questionRepository.findById(id);
        QuestionDto questionDto = maybeQuestion.map(question -> question.update(loginUser, updatedQuestionDto)).orElseThrow(ForbiddenRequestException::new);
        questionRepository.save(maybeQuestion.get());
        return questionDto;
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long id) throws CannotDeleteException {
        // TODO 삭제 기능 구현
    }

    public Iterable<Question> findAll() {
        return questionRepository.findByDeleted(false);
    }

    public List<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable).getContent();
    }

    public Answer addAnswer(User loginUser, long questionId, String contents) {
        return null;
    }

    public Answer deleteAnswer(User loginUser, long id) {
        // TODO 답변 삭제 기능 구현 
        return null;
    }
}
