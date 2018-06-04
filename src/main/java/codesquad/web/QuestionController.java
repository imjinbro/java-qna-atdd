package codesquad.web;

import codesquad.ForbiddenRequestException;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import support.domain.EntityName;
import support.domain.ViewPath;

import static support.domain.EntityName.getModelName;
import static support.domain.ViewPath.getViewPath;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    private QnaService qnaService;

    @Autowired
    public QuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping
    public String create(@LoginUser User loginUser, QuestionDto questionDto) {
        return qnaService.create(loginUser, questionDto).generateRedirectUrl();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) throws ForbiddenRequestException {
        Question question = qnaService.findById(id);
        log.debug("question : {} ", question);
        model.addAttribute(getModelName(EntityName.QUESTION), question);
        return getViewPath(ViewPath.QNA_SHOW);
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
