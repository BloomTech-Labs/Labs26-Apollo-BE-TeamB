package com.lambdaschool.apollo.views;

import java.util.ArrayList;
import java.util.List;

public class QuestionBodyConsumer {
    private List<QuestionBody> questionBodyList;

    public QuestionBodyConsumer() {
        questionBodyList = new ArrayList<>();
    }

    public List<QuestionBody> getQuestionBodyList() {
        return questionBodyList;
    }

    public void setQuestionBodyList(List<QuestionBody> questionBodyList) {
        this.questionBodyList = questionBodyList;
    }
}
