package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "questions")
public class Question extends Auditable {

    /**
     * The primary key (long) of the questions table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long questionId;

    /**
     * The body (String) describes the question. Cannot be null
     */
    @NotNull
    @Column(nullable = false)
    private String body;

    /**
     * The isLeader (boolean) differs leader questions. Cannot be null
     */
    @NotNull
    @Column(nullable = false)
    private boolean isLeader;

    /**
     * The type (String) of question. Cannot be null
     */
    @NotNull
    @Column(nullable = false)
    private String type;

    /**
     * The surveyid of the survey assigned to this question is what is stored in the database.
     * This is the entire survey object!
     * <p>
     * Forms a Many to One relationship between questions and survey.
     * A survey can have many questions.
     */
    @ManyToOne
    @JoinColumn(name = "surveyId", nullable = false)
    @JsonIgnoreProperties(value = "questions", allowSetters = true)
    private Survey survey;

    /**
     * Default constructor used primarily by the JPA.
     */
    public Question() {
    }

    /**
     * Given the params, create a new question object
     * <p>
     * questionId is autogenerated
     *
     * @param body     The body (String) of the question
     * @param isLeader The isLeader (boolean) of the question
     * @param type     The type (String) of the question
     * @param survey   The survey (Survey) connected to the question
     */
    public Question(String body, boolean isLeader, String type, Survey survey) {
        setBody(body);
        setLeader(isLeader);
        setType(type);
        setSurvey(survey);
    }

    /**
     * Getter for questionId
     *
     * @return the questionId (long) of the question
     */
    public long getQuestionId() {
        return questionId;
    }

    /**
     * Setter for questionId. Used primary for seeding data
     *
     * @param questionId the new questionId (long) of the question
     */
    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    /**
     * Getter for body
     *
     * @return the body (String) of the question
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter for body. Used primary for seeding data
     *
     * @param body the new body (String) of the question
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Getter for isLeader
     *
     * @return the isLeader (boolean) of the question
     */
    public boolean isLeader() {
        return isLeader;
    }

    /**
     * Setter for isLeader. Used primary for seeding data
     *
     * @param leader the new isLeader (boolean) of the question
     */
    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    /**
     * Getter for type
     *
     * @return the type (String) of the question
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type. Used primary for seeding data
     *
     * @param type the new type (String) of the question
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for survey
     *
     * @return the survey (Survey) of the question
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Setter for survey. Used primary for seeding data
     *
     * @param survey the new survey (Survey) of the question
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }


}
