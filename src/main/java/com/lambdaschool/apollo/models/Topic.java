package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.apollo.views.TopicFrequency;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
public class Topic extends Auditable {

    /**
     * The primary key (long) of the topics table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "topicId")
    private long topicId;

    /**
     * The title (String). Cannot be null
     */
    @NotNull
    @Column(nullable = false)
    private String title;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"ownedtopics", "topics", "primaryemail", "roles", ""}, allowSetters = true)
    private User owner;

    private TopicFrequency frequency;

    /**
     * The surveyid of the survey assigned to this topic is what is stored in the database.
     * This is the entire survey object!
     * <p>
     * Forms a Many to One relationship between topics and survey.
     * A survey can have many topics.
     */
    //ID of the defaul survey a topic uses to prepopulate any survey request
    private long defaultsurveyid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "surveyId")
    @JsonIgnoreProperties(value = {"defaulttopic", "topic"}, allowSetters = true)
    private Survey defaultsurvey;

    private String joincode;

    //Survey Requests that an owner of a topic has generated
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"topic", "defaulttopic"}, allowSetters = true)
    private List<Survey> surveysrequests = new ArrayList<>();

    /**
     * Forms an One to Many relationship between topic and users.
     * A topic can have many users.
     */
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"topic", "primaryemail", "roles", "ownedtopics"}, allowSetters = true)
    private List<TopicUsers> users = new ArrayList<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public Topic() {
    }

    public Topic(@NotNull String title, User owner, Survey survey, TopicFrequency frequency) {
        this.title = title;
        this.owner = owner;
        this.defaultsurvey = survey;
        this.frequency = frequency;
    }

    public Topic(@NotNull String title, User owner, long defaultsurveyid, List<TopicUsers> users, TopicFrequency frequency) {
        this.title = title;
        this.owner = owner;
        this.defaultsurveyid = defaultsurveyid;
        for (TopicUsers tu : users) {
            tu.setTopic(this);
        }
        this.users = users;
        this.frequency = frequency;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Survey> getSurveysrequests() {
        return surveysrequests;
    }

    public void setSurveysrequests(List<Survey> surveysrequests) {
        this.surveysrequests = surveysrequests;
    }

    public List<TopicUsers> getUsers() {
        return users;
    }

    public void setUsers(List<TopicUsers> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(new TopicUsers(this, user));
    }



    public long getDefaultsurveyid() {
        return defaultsurveyid;
    }

    public void setDefaultsurveyid(long defaultsurveyid) {
        this.defaultsurveyid = defaultsurveyid;
    }

    public TopicFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(TopicFrequency frequency) {
        this.frequency = frequency;
    }

    public String getJoincode() {
        return joincode;
    }

    public void setJoincode(String joincode) {
        this.joincode = joincode;
    }

    public Survey getDefaultsurvey() {
        return defaultsurvey;
    }

    public void setDefaultsurvey(Survey defaultsurvey) {
        this.defaultsurvey = defaultsurvey;
    }
}
