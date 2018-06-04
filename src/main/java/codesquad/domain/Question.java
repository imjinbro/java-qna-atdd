package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.ForbiddenRequestException;
import codesquad.UnAuthorizedException;
import codesquad.dto.QuestionDto;
import org.hibernate.annotations.Where;
import support.domain.AbstractEntity;
import support.domain.UrlGeneratable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends AbstractEntity implements UrlGeneratable {
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @Size(min = 3)
    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    private boolean deleted = false;

    public Question() {
    }

    public Question(String title, String contents) {
        this(0L, title, contents);
    }

    public Question(long id, String title, String contents) {
        super(id);
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public User getWriter() {
        return writer;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String generateUrl() {
        return String.format("/questions/%d", getId());
    }

    public QuestionDto toQuestionDto() {
        return new QuestionDto(getId(), this.title, this.contents);
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }

    public QuestionDto update(User loginUser, QuestionDto updatedQuestionDto) {
        if (!isMatch(updatedQuestionDto)) {
            throw new ForbiddenRequestException("not same question");
        }
        validateAuthorize(loginUser);
        title = updatedQuestionDto.getTitle();
        contents = updatedQuestionDto.getContents();
        return toQuestionDto();
    }

    public void delete(User loginUser) throws CannotDeleteException, UnAuthorizedException {
        if (isDeleted()) {
            throw new CannotDeleteException("");
        }
        validateAuthorize(loginUser);
        deleted = true;
    }

    private boolean isMatch(QuestionDto questionDto) {
        long requestId = questionDto.getId();
        return getId() == requestId;
    }

    private void validateAuthorize(User loginUser) throws UnAuthorizedException {
        if (!writer.equals(loginUser)) {
            throw new UnAuthorizedException("not match writer");
        }
    }
}
