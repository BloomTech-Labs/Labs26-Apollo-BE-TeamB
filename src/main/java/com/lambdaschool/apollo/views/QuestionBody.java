package com.lambdaschool.apollo.views;

import javax.persistence.Entity;

public class QuestionBody {
    private String body;
    private long questionid;

    public QuestionBody() {
    }

    public QuestionBody(String body, long questionid) {
        this.body = body;
        this.questionid = questionid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(long questionid) {
        this.questionid = questionid;
    }

    @Override
    public String toString() {
        return "QuestionBody{" +
                "body='" + body + '\'' +
                ", questionid=" + questionid +
                '}';
    }
}
