package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Survey", description = "This entity is a combination of surveys and survey requests")
@Entity
@Table(name = "surveys")
public class Survey extends Auditable {

    /**
     * The primary key (long) of the surveys table.
     */
    @ApiModelProperty(name = "Survey id", value = "primary key for survey", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long surveyid;

    @ApiModelProperty(name = "Topic id", value = "Topic id for this survey request, can be null if this is used as survey", allowEmptyValue = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topicid")
    @JsonIgnoreProperties(value = {"surveysrequests", "owner", "defaultsurveyid", "users"}, allowSetters = true)
    private Topic topic;

    @ApiModelProperty(name = "Topic id", value = "Topic id ")
    @OneToMany(mappedBy = "defaultsurvey", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"defaultsurvey", "survey"}, allowSetters = true)
    private List<Topic> defaulttopic;

    @ApiModelProperty(name = "Question id", value = "Question associated with this survey")
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "survey", allowSetters = true)
    private List<Question> questions = new ArrayList<>();

    @ApiModelProperty(name = "Responded boolean", value = "Info regarding if the user has responded to this survey")
    @Transient
    private boolean responded = false;

    /**
     * Default constructor used primarily by the JPA.
     */
    public Survey() {
    }

    public Survey(Topic topic) {
        this.topic = topic;
    }

    public long getSurveyId() {
        return surveyid;
    }

    public void setSurveyId(long surveyid) {
        this.surveyid = surveyid;
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

    public long getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(long surveyid) {
        this.surveyid = surveyid;
    }

    public boolean isResponded() {
        return responded;
    }

    public void setHasResponded(boolean isResponded) {
        this.responded = isResponded;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "surveyid=" + surveyid +
                ", topic=" + topic +
                ", defaulttopic=" + defaulttopic +
                ", questions=" + questions +
                '}';
    }
}
