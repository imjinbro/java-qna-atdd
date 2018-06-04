package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QnaAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private ResponseEntity<String> create(TestRestTemplate template) throws Exception {
        HtmlFormDataBuilder builder = HtmlFormDataBuilder.encodeform();
        builder.addParameter("title", "test~~~~~!");
        builder.addParameter("contents", "test content");
        HttpEntity<MultiValueMap<String, Object>> request = builder.build();
        return template.postForEntity("/questions", request, String.class);
    }

    @Test
    public void create() throws Exception {
        ResponseEntity<String> response = create(basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        log.debug("question created body : {}", response.getBody());
    }

    @Test
    public void create_fail_require_login() throws Exception {
        ResponseEntity<String> response = create(template());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
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
    public void delete() {
    }

    @Test
    public void delete_fail_not_owner() {
    }
}
