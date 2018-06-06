package codesquad.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiQnaAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiQnaAcceptanceTest.class);

    @Test
    public void create_answer() {
    }

    @Test
    public void create_answer_fail_unAuthentication() {
    }

    @Test
    public void create_answer_fail_invalid_answer() {
    }

    @Test
    public void read() {
    }

    @Test
    public void read_fail_deleted() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void edit_fail_unAuthentication() {
    }

    @Test
    public void edit_fail_unAuthorized() {
    }

    @Test
    public void edit_fail_deleted() {
    }

    @Test
    public void update() {
    }

    @Test
    public void update_fail_unAuthentication() {
    }

    @Test
    public void update_fail_unAuthorized() {
    }

    @Test
    public void update_fail_deleted() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void delete_fail_unAuthentication() {
    }

    @Test
    public void delete_fail_unAuthorized() {
    }

    @Test
    public void delete_fail_already_delete() {
    }
}
