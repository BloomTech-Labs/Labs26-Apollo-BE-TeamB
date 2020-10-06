package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "answers")
@JsonIgnoreProperties(value = {"survey"}, allowSetters = true)
public class Answer extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long answerid;

    @NotNull
    @Column(nullable = false)
    private String body;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionid")
    @JsonIgnoreProperties(value = {"contexts", "survey", "answers"}, allowSetters = true)
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = {"primaryemail", "topics", "roles", "ownedtopics"})
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "surveyid")
    private Survey survey;

    public Answer() {
    }

    public Answer(String body, Question question, User user, Survey survey) {
        this.body = body;
        this.question = question;
        this.user = user;
        this.survey = survey;
    }

    public long getAnswerId() {
        return answerid;
    }

    public void setAnswerId(long answerid) {
        this.answerid = answerid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
