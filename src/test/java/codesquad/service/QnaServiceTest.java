package codesquad.service;

import codesquad.ForbiddenRequestException;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    private QuestionRepository questionRepo;

    @InjectMocks
    private QnaService qnaService;

    private Question question;

    private User user;
    private User otherUser;

    @Before
    public void setUp() throws Exception {
        user = new User("colin", "password", "colin", "colin@codesquad.kr");
        otherUser = new User("jinbro", "password", "jinbro", "jinbro@codesquad.kr");
        question = new Question("test", "testing!");
    }

    @Test
    public void create() {
        qnaService.create(user, question.toQuestionDto());
        verify(questionRepo, times(1)).save(question);
    }

    @Test(expected = ForbiddenRequestException.class)
    public void read_not_exist() {
        when(questionRepo.findById(question.getId())).thenReturn(Optional.empty());
        qnaService.findById(anyLong());
    }

    @Test(expected = ForbiddenRequestException.class)
    public void read_fail_not_exist() {
        qnaService.findById(10000L);
    }

    @Test
    public void update() {
    }

    @Test
    public void update_fail_require_login() {

    }

    @Test
    public void update_fail_not_math_writer() {

    }

    @Test
    public void update_fail_min_title() {

    }

    @Test
    public void update_fail_max_title() {

    }

    @Test
    public void update_fail_min_contents() {

    }

    @Test
    public void delete() {
    }

    @Test(expected = AuthenticationException.class)
    public void delete_fail_require_login() {

    }

    @Test
    public void delete_fail_not_owner() {
    }
}