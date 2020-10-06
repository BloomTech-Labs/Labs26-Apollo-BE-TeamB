package com.lambdaschool.apollo.views;

import com.lambdaschool.apollo.models.Question;

import java.util.List;

public class Response {
    private long userid;
    private List<Question> questions;

    public Response() {
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Response{" +
                "userid=" + userid +
                ", questions=" + questions +
                '}';
    }
}


