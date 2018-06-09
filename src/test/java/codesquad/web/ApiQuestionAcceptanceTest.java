package codesquad.web;

import codesquad.dto.QuestionDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiQuestionAcceptanceTest extends AcceptanceTest {
    private static final String PATH_CREATE = "/api/questions";
    private static final String PATH_INVALID_SHOW = "/api/questions/100";

    private QuestionDto validQuestionDto() {
        return new QuestionDto("test", "content");
    }

    private QuestionDto updateQuestionDto() {
        return new QuestionDto("updateQuestion title", "updateQuestion content");
    }

    private QuestionDto invalidQuestionDto() {
        return new QuestionDto("", "");
    }

    @Test
    public void create() {
        QuestionDto reqData = validQuestionDto();
        String resPath = createResource(basicAuthTemplate(), PATH_CREATE, reqData);
        QuestionDto questionDto = requestGetForRest(basicAuthTemplate(), resPath, QuestionDto.class);

        assertEquals(reqData.getTitle(), questionDto.getTitle());
        assertEquals(reqData.getContents(), questionDto.getContents());
    }

    @Test
    public void create_fail_unAuthentication() {
        ResponseEntity<String> response = requestPost(template(), PATH_CREATE, validQuestionDto());
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void create_fail_invalid_answer() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), PATH_CREATE, invalidQuestionDto());
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void read() {
        QuestionDto newQuestion = validQuestionDto();
        String location = createResource(basicAuthTemplate(), PATH_CREATE, newQuestion);

        ResponseEntity<QuestionDto> response = requestGet(location, QuestionDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
        ;
    }

    @Test
    public void read_fail_deleted() {
        ResponseEntity<QuestionDto> response = requestGet(PATH_INVALID_SHOW, QuestionDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void update() {
        QuestionDto update = updateQuestionDto();
        String path = createResource(basicAuthTemplate(), PATH_CREATE, validQuestionDto());
        basicAuthTemplate().put(path, update);

        QuestionDto current = requestGetForRest(basicAuthTemplate(), path, QuestionDto.class);
        assertEquals(update.getContents(), current.getContents());
    }

    @Test
    public void update_fail_unAuthentication() {
        QuestionDto update = updateQuestionDto();
        String path = createResource(basicAuthTemplate(), PATH_CREATE, validQuestionDto());
        template().put(path, update);

        QuestionDto current = requestGetForRest(basicAuthTemplate(), path, QuestionDto.class);
        assertNotEquals(update.getContents(), current.getContents());
    }

    @Test
    public void update_fail_unAuthorized() {
        QuestionDto update = updateQuestionDto();
        String path = createResource(basicAuthTemplate(), PATH_CREATE, validQuestionDto());
        basicAuthTemplate(findByUserId("sanjigi")).put(path, update);

        QuestionDto current = requestGetForRest(basicAuthTemplate(), path, QuestionDto.class);
        assertNotEquals(update.getContents(), current.getContents());
    }

    @Test
    public void delete() {
        String path = createResource(basicAuthTemplate(), PATH_CREATE, validQuestionDto());
        basicAuthTemplate().delete(path);

        ResponseEntity<QuestionDto> response = requestGet(path, QuestionDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void delete_fail_unAuthentication() {
        String path = createResource(basicAuthTemplate(), PATH_CREATE, validQuestionDto());
        template().delete(path);

        ResponseEntity<QuestionDto> response = requestGet(path, QuestionDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
    }

    @Test
    public void delete_fail_unAuthorized() {
        String path = createResource(basicAuthTemplate(), PATH_CREATE, validQuestionDto());
        basicAuthTemplate(findByUserId("sanjigi")).delete(path);

        ResponseEntity<QuestionDto> response = requestGet(path, QuestionDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
    }

    @Test
    public void delete_fail_exist_otherUser_Answer() {
    }
}
