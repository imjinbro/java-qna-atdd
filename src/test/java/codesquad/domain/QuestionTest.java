package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    private Question question;
    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        user1 = new User("colin", "password", "colin", "colin@codesqaud.kr");
        user2 = new User("jinbro", "password", "jinbro", "jinbro@codesqaud.kr");

        question = new Question("test", "test");
        question.writeBy(user1);
    }

    @Test
    public void owner() {
        assertTrue(question.isOwner(user1));
    }

    @Test
    public void not_owner() {
        assertFalse(question.isOwner(user2));
    }

    @Test
    public void deleted() throws Exception {
        question.delete(user1);
        assertTrue(question.isDeleted());
    }

    @Test
    public void not_delete() {
        assertFalse(question.isDeleted());
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_fail_not_match_user() throws Exception {
        question.delete(user2);
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail_already_delete() throws Exception {
        question.delete(user1);
        question.delete(user1);
    }
}