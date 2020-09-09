package com.lambdaschool.foundation.models;

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

    /**
     * Default constructor used primarily by the JPA.
     */
    public Survey() {
    }

    /**
     * Getter for surveyId
     *
     * @return the surveyId (long) of the survey
     */
    public long getSurveyId() {
        return surveyId;
    }

    /**
     * Setter for surveyId. Used primary for seeding data
     *
     * @param surveyId the new surveyId (long) of the survey
     */
    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

}
