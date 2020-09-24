package com.lambdaschool.apollo.models;

public class AnswerMinimum {
    private String body;
    private long questionId;

    public AnswerMinimum(String body, long questionId, long surveyId) {
        this.body = body;
        this.questionId = questionId;
    }

    public AnswerMinimum() {
    }

    public AnswerMinimum(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

}
