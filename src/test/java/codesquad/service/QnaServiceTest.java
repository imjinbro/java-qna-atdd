package codesquad.service;

import codesquad.domain.QuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    private QuestionRepository questionRepo;

    @InjectMocks
    private QnaService qnaService;

    @Test
    public void create() {
    }

    @Test
    public void create_fail_require_login() {
    }

    @Test
    public void create_fail_min_title() {
    }

    @Test
    public void create_fail_max_title() {
    }

    @Test
    public void create_fail_min_contents() {
    }

    @Test
    public void read() {
    }

    @Test
    public void read_fail_status_deleted() {
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

    @Test
    public void delete_fail_not_owner() {
    }
}