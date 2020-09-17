package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

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
    @JsonIgnoreProperties({"surveyrequests"})
    private Topic topic;

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
}
