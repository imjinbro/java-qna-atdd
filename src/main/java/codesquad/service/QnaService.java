package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.dto.AnswerDto;
import codesquad.dto.QuestionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service("qnaService")
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    @Resource(name = "questionRepository")
    private QuestionRepository questionRepo;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepo;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Question create(User loginUser, QuestionDto questionDto) {
        Question question = questionDto.toQuestion();
        question.writeBy(loginUser);
        return questionRepo.save(question);
    }

    public Question findQuestionById(Long id) {
        return questionRepo.findById(id).filter(question -> !question.isDeleted()).orElseThrow(EntityNotFoundException::new);
    }

    public Question findQuestionById(User loginUser, Long id) {
        Question question = findQuestionById(id);
        if (!question.isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        return question;
    }

    public Answer findAnswerById(Long id) {
        return answerRepo.findById(id).filter(answer -> !answer.isDeleted()).orElseThrow(EntityNotFoundException::new);
    }

    public QuestionDto updateQuestion(User loginUser, Long id, QuestionDto updatedQuestionDto) {
        Optional<Question> maybeQuestion = questionRepo.findById(id);
        QuestionDto questionDto = maybeQuestion.map(question -> question.update(loginUser, updatedQuestionDto)).orElseThrow(EntityNotFoundException::new);
        questionRepo.save(questionDto.toQuestion());
        return questionDto;
    }

    public AnswerDto updateAnswer(User loginUser, Long id, AnswerDto updatedAnswer) {
        Optional<Answer> maybeAnswer = answerRepo.findById(id);
        AnswerDto answerDto = maybeAnswer.map(answer -> answer.update(loginUser, updatedAnswer)).orElseThrow(EntityNotFoundException::new);
        answerRepo.save(answerDto.toAnswer());
        return answerDto;
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long id) throws CannotDeleteException {
        deleteHistoryService.saveAll(findQuestionById(id).delete(loginUser));
    }

    public List<Question> findAll() {
        return questionRepo.findByDeleted(false);
    }

    public List<Question> findAll(Pageable pageable) {
        return questionRepo.findAll(pageable).getContent();
    }

    public Answer addAnswer(User loginUser, long questionId, AnswerDto answerDto) {
        Answer answer = answerDto.toAnswer().writeBy(loginUser).toQuestion(questionRepo.findById(questionId).orElseThrow(EntityNotFoundException::new));
        answerRepo.save(answer);
        return answer;
    }

    public String deleteAnswer(User loginUser, long id) throws CannotDeleteException {
        Answer answer = answerRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        deleteHistoryService.save(answer.delete(loginUser));
        answerRepo.save(answer);
        return answer.questionPath();
    }
}
