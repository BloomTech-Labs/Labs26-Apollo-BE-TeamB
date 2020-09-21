package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "surveys")
public class Survey extends Auditable {

    /**
     * The primary key (long) of the surveys table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long surveyId;


    @ManyToOne
    @JoinColumn(name = "topicId")
    @JsonIgnoreProperties({"surveysrequests", "owner", "defaultsurveyid", "users"})
    private Topic topic;

    @OneToMany(mappedBy = "defaultsurvey", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"defaultsurvey", "survey"})
    private List<Topic> defaulttopic;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("survey")
    private List<Question> questions = new ArrayList<>();


    /**
     * Default constructor used primarily by the JPA.
     */
    public Survey() {
    }

    public Survey(Topic topic) {
        this.topic = topic;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Topic> getDefaulttopic() {
        return defaulttopic;
    }

    public void setDefaulttopic(List<Topic> defaulttopic) {
        this.defaulttopic = defaulttopic;
    }
}
