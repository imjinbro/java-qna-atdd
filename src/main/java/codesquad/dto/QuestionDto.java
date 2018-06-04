package codesquad.dto;

import codesquad.domain.Question;

import javax.validation.constraints.Size;

public class QuestionDto {
    private Long id;

    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3)
    private String contents;

    public QuestionDto() {}

    public QuestionDto(String title, String contents) {
        this(0, title, contents);
    }

    public QuestionDto(long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public QuestionDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public QuestionDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public QuestionDto setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public Question toQuestion() {
        return new Question(this.title, this.contents);
    }
}
