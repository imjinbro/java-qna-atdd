package codesquad.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QnaAcceptanceTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

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
