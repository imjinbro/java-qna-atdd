package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.dto.QuestionDto;
import codesquad.dto.UserDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ApiQuestionAcceptaceTest extends AcceptanceTest {

    private QuestionDto createQuestionDto(String title) {
        return new QuestionDto(title, "contents");
    }

    private AnswerDto createAnswerDto(QuestionDto question) {
        return createAnswerDto("test answer", question);
    }

    private AnswerDto createAnswerDto(String contents, QuestionDto question) {
        return new AnswerDto()
                .setWriter(defaultUser())
                .setContents(contents)
                .setQuestion(new Question()
                        .setTitle(question.getTitle())
                        .setContents(question.getContents()));

    }

    @Test
    public void create() throws Exception {
        QuestionDto questionDto = createQuestionDto("test2");
        String location = createResourceDefaultLogin("/api/questions", questionDto);
        QuestionDto dbQuestion = getResource(location, QuestionDto.class);

        assertThat(dbQuestion, is(questionDto));
    }

    @Test
    public void showQuestionDetail() throws Exception {
        QuestionDto questionDto = createQuestionDto("test2");
        String location = createResourceDefaultLogin("/api/questions", questionDto);
        ResponseEntity<String> response = template().getForEntity(location, String.class);

        assertThat(response.getBody().contains("test2"), is(true));
    }

    @Test
    public void update() throws Exception {
        QuestionDto questionDto = createQuestionDto("test3");
        String location = createResourceDefaultLogin("/api/questions", questionDto);

        QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);
        QuestionDto updateQuestion = new QuestionDto(dbQuestion.getId(), "new title", "new contents");
        basicAuthTemplate().put(location, updateQuestion);

        dbQuestion = basicAuthTemplate(defaultUser()).getForObject(location, QuestionDto.class);
        assertThat(dbQuestion, is(updateQuestion));
    }

    @Test
    public void update_no_authroity() throws Exception {
        QuestionDto questionDto = createQuestionDto("test4");
        String location = createResourceDefaultLogin("/api/questions", questionDto);

        QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);
        QuestionDto updateQuestion = new QuestionDto(dbQuestion.getId(), "new title", "new contents");
        basicAuthTemplate().put(location, updateQuestion);

        User user = new User("sehwan", "1001", "sehwan", "email");
        dbQuestion = basicAuthTemplate(user).getForObject(location, QuestionDto.class);
        assertThat(dbQuestion, not(questionDto));
    }

    @Test
    public void delete_no_comments() throws Exception {
        QuestionDto questionDto = createQuestionDto("test5");
        String location = createResourceDefaultLogin("/api/questions", questionDto);

        QuestionDto dbQuestion = template().getForObject(location, QuestionDto.class);
        basicAuthTemplate().delete(location, dbQuestion);

        dbQuestion = getResource(location, QuestionDto.class);
        assertNull(dbQuestion);
    }

    @Test
    public void delete_contains_only_comments_written_by_the_writer() throws Exception {
        QuestionDto questionDto = createQuestionDto("test5");
        String location = createResourceDefaultLogin("/api/questions", questionDto);

        QuestionDto dbQuestion = template().getForObject(location, QuestionDto.class);
        User user = new User("sehwan", "1001", "sehwan", "email");

        AnswerDto answerDto = createAnswerDto(dbQuestion);
        location = createResourceDefaultLogin("/api/questions/" + dbQuestion.getId() +"/answers", answerDto);
        AnswerDto dbAnswer = getResource(location, AnswerDto.class);

        basicAuthTemplate(user).delete(location, dbQuestion);

        dbQuestion = getResource(location, QuestionDto.class);
        assertNotNull(dbQuestion);
    }

    @Test
    public void delete_no_authority() throws Exception {
        QuestionDto questionDto = createQuestionDto("test5");
        String location = createResourceDefaultLogin("/api/questions", questionDto);

        QuestionDto dbQuestion = template().getForObject(location, QuestionDto.class);
        User user = new User("sehwan", "1001", "sehwan", "email");
        basicAuthTemplate(user).delete(location, dbQuestion);

        dbQuestion = getResource(location, QuestionDto.class);
        assertNotNull(dbQuestion);
    }

    @Test
    public void delete_no_authority_contains_comments_written_by_others() throws Exception {
        QuestionDto questionDto = createQuestionDto("test5");
        String location = createResourceDefaultLogin("/api/questions", questionDto);

        QuestionDto dbQuestion = template().getForObject(location, QuestionDto.class);
        User user = new User("sehwan", "1001", "sehwan", "email");

        AnswerDto answerDto = createAnswerDto(dbQuestion);
        answerDto.setWriter(new User("sehwan", "test", "sehwan", "sehwan@woowa.com"));
        location = createResourceDefaultLogin("/api/questions/" + dbQuestion.getId() +"/answers", answerDto);
        AnswerDto dbAnswer = getResource(location, AnswerDto.class);

        basicAuthTemplate(user).delete(location, dbQuestion);

        dbQuestion = getResource(location, QuestionDto.class);
        assertNotNull(dbQuestion);
    }
}