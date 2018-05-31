package codesquad.web;

import codesquad.ForbiddenRequestException;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QnaService qnaService;

    @Autowired
    public QuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping
    public String create(@LoginUser User loginUser, Question question) {
        Question savedQuestion = qnaService.create(loginUser, question);
        return savedQuestion.generateUrl();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) throws ForbiddenRequestException {

        return null;
    }

    @GetMapping("/{id}/form")
    public String edit(@LoginUser User loginUser, @PathVariable Long id, Model model) throws ForbiddenRequestException {

        return null;
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, Question updateQuestion) {

        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {

        return null;
    }
}
