package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {
    private static final Logger log = LoggerFactory.getLogger(ApiQuestionController.class);

    @Resource
    private QnaService qnaService;

    @PostMapping
    public ResponseEntity<Void> create(@LoginUser User user, @Valid @RequestBody QuestionDto questionDto) {
        Question question = qnaService.create(user, questionDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(question.generateApiUrl()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> show(@PathVariable Long id) {
        return new ResponseEntity<>(qnaService.findById(id).toQuestionDto(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@LoginUser User loginUser, @PathVariable Long id, @Valid @RequestBody QuestionDto updateQuestionDto) {
        Question question = qnaService.update(loginUser, id, updateQuestionDto).toQuestion();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(question.generateApiUrl()));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long id) throws CannotDeleteException {
        qnaService.deleteQuestion(loginUser, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }



}
