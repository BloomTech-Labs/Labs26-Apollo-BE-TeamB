package com.lambdaschool.apollo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.apollo.views.TopicFrequency;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Topic", description = "Topic model")
@Entity
@Table(name = "topics")
public class Topic extends Auditable {

    /**
     * The primary key (long) of the topics table.
     */
    @ApiModelProperty(name = "Topic id", value = "primary key for Topic", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "topicId")
    private long topicId;

    /**
     * The title (String). Cannot be null
     */
    @ApiModelProperty(name = "Topic title", value = "Topic title", example = "Some title")
    @NotNull
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(name = "Owner", value = "Owner id for this topic")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"ownedtopics", "topics", "primaryemail", "roles", ""}, allowSetters = true)
    private User owner;

    @ApiModelProperty(name = "Frequency", value = "Topic Frequency", example = "Daily")
    private TopicFrequency frequency;

    @ApiModelProperty(name = "Survey id", value = "Default Survey Id used for this topic")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "surveyId")
    @JsonIgnoreProperties(value = {"defaulttopic", "topic"}, allowSetters = true)
    private Survey defaultsurvey;

    @ApiModelProperty(name = "Join code", value = "Join code for this topic", example = "xt23sRvxD")
    private String joincode;

    //Survey Requests that an owner of a topic has generated
    @ApiModelProperty(name = "Survery Request id", value = "Survey requested using this topic")
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"topic", "defaulttopic"}, allowSetters = true)
    @JsonIgnore
    private List<Survey> surveysrequests = new ArrayList<>();

    /**
     * Forms an One to Many relationship between topic and users.
     * A topic can have many users.
     */
    @ApiModelProperty(name = "Members", value = "Users (Members) of this topic")
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"topic", "primaryemail", "roles", "ownedtopics"}, allowSetters = true)
    @JsonIgnore
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

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title + '\'' +
                ", owner=" + owner +
                '}';
    }
}
