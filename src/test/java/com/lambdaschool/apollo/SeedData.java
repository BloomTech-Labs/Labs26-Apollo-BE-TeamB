package com.lambdaschool.apollo;

import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.repository.*;
import com.lambdaschool.apollo.services.*;
import com.lambdaschool.apollo.views.QuestionType;
import com.lambdaschool.apollo.views.TopicFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ContextService contextService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    @Override
    public void run(String[] args) throws Exception {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleRepository.save(r1); // id 1
        r2 = roleRepository.save(r2); // id 2
        r3 = roleRepository.save(r3); // id 3

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        admins.add(new UserRoles(new User(), r3));
        User u1 = new User("admin", "admin@lambdaschool.local", admins);
        userRepository.save(u1); // id 4

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), r3));
        datas.add(new UserRoles(new User(), r2));
        User u2 = new User("user1", "user1@user.com", datas);
        userRepository.save(u2); // id 5

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("user2", "user2@user.com", users);
        userRepository.save(u3); // id 6

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("user3", "user3@user.com", users);
        userRepository.save(u4); // id 7

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("user4", "user4@user.com", users);
        userRepository.save(u5); // id 8

        Survey s1 = surveyRepository.save(new Survey()); // id 9
        Topic topic2 = new Topic("Topic 2", u1, s1, TopicFrequency.MONDAY);
        topic2 = topicRepository.save(topic2); // id 10
        topicService.addTopicUser(topic2.getTopicId(), u2.getUserid());
        topicService.addTopicUser(topic2.getTopicId(), u3.getUserid());
        topic2.setJoincode("topic2joincode");

        Survey s2 = surveyRepository.save(new Survey()); // id 11
        Topic topic3 = new Topic("Topic 3", u2, s2, TopicFrequency.WEEKLY);
        topic3 = topicRepository.save(topic3); // id 12
        topicService.addTopicUser(topic3.getTopicId(), u4.getUserid());

        Survey s3 = surveyRepository.save(new Survey()); // id 13
        Topic topic4 = new Topic("Topic 4", u2, s3, TopicFrequency.WEEKLY);
        topic4 = topicRepository.save(topic4); // id 14

        Survey s4 = surveyRepository.save(new Survey()); // id 15
        Topic topic5 = new Topic("Topic 5", u2, s4, TopicFrequency.MONTHLY);
        topic5 = topicRepository.save(topic5); // id 16

        Survey s5 = surveyRepository.save(new Survey()); // id 17
        Topic topic6 = new Topic("Topic 6", u2, s5, TopicFrequency.MONTHLY);
        topic6 = topicRepository.save(topic6); // id 18


        /******************************************************************/
        // survey
        Survey s6 = new Survey(topic3);
        s6 = surveyRepository.save(s6); // id 19

        Survey s7 = new Survey(topic4);
        s7 = surveyRepository.save(s7); // id 20

        Survey s8 = new Survey(topic5);
        s8 = surveyRepository.save(s8); // id 21

        Survey s10 = new Survey(topic6);
        s10 = surveyRepository.save(s10); // id 22

        Survey s11 = new Survey(topic2);
        s11 = surveyRepository.save(s11); // id 23
        List<Survey> requests = topic2.getSurveysrequests();
        requests.add(new Survey(topic2));
        topic2.setSurveysrequests(requests);

        // context
        Context context1 = new Context("Product Leadership", s1);
        contextRepository.save(context1); // id 24

        Context context2 = new Context("Delivery Management", s2);
        contextRepository.save(context2); // id 25

        Context context3 = new Context("Project Management", s3);
        contextRepository.save(context3); // id 26

        Context context4 = new Context("Design Leadership", s4);
        contextRepository.save(context4); // id 27

        Context context5 = new Context("Engineering Leadership", s5);
        contextRepository.save(context5); // id 28

        /*******************************************************************/
        // questions

        Question question1 = new Question("Leader Question 1", true, QuestionType.TEXT, s1);
        question1 = questionRepository.save(question1); // id 29

        Question question2 = new Question("Leader Question 2", true, QuestionType.TEXT, s1);
        question2 = questionRepository.save(question2); // id 30

        Question question3 = new Question("Member Question 1", false, QuestionType.TEXT, s1);
        question3 = questionRepository.save(question3); // id 31

        Question question4 = new Question("Member Question 2", false, QuestionType.TEXT, s2);
        question4 = questionRepository.save(question4); // id 32

        Question question5 = new Question("Member Question 3", false, QuestionType.TEXT, s2);
        question5 = questionRepository.save(question5); // id 33

        /*******************************************************************/
        // answers
        Answer answer1 = new Answer("test answer 1", question1, u1, s1);
        answer1 = answerRepository.save(answer1);

        // this.body = body;
        // this.question = question;
        // this.user = user;
        // this.survey = survey;
    }
}