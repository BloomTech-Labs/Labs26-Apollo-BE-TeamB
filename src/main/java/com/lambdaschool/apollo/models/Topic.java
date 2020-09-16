package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private long topicId;

    /**
     * The title (String). Cannot be null
     */
    @NotNull
    @Column(nullable = false)
    private String title;


    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"ownedtopics", "topics", "primaryemail", "roles", ""}, allowSetters = true)
    private User owner;

    /**
     * The surveyid of the survey assigned to this topic is what is stored in the database.
     * This is the entire survey object!
     * <p>
     * Forms a Many to One relationship between topics and survey.
     * A survey can have many topics.
     */

    private long defaultsurveyid;


    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"topic"})
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

    public Topic(@NotNull String title, User owner, Survey survey) {
        this.title = title;
        this.owner = owner;
        this.defaultsurveyid = survey.getSurveyId();
    }

    public Topic(@NotNull String title, User owner, long defaultsurveyid, List<TopicUsers> users) {
        this.title = title;
        this.owner = owner;
        this.defaultsurveyid = defaultsurveyid;
        for (TopicUsers tu : users) {
            tu.setTopic(this);
        }
        this.users = users;
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
}
