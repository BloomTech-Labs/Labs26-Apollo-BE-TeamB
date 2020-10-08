package com.lambdaschool.apollo;

import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.services.*;
import com.lambdaschool.apollo.views.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
//Comment this out if the Database is breaking
@Component

public class SeedData
        implements CommandLineRunner {
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    /**
     * Connects the survey service to this process
     */
    @Autowired
    SurveyService surveyService;

    /**
     * Connects the context service to this process
     */
    @Autowired
    ContextService contextService;

    /**
     * Connects the question service to this process
     */
    @Autowired
    QuestionService questionService;

    /**
     * Connects the topic service to this process
     */
    @Autowired
    TopicService topicService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args)
            throws
            Exception {

        User u1 = new User("llama001@maildrop.cc", "llama001@maildrop.cc");
        User u2 = new User("llama002@maildrop.cc", "llama002@maildrop.cc");
        User u3 = new User("llama003@maildrop.cc", "llama003@maildrop.cc");

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);

        Survey survey1 = surveyService.save(new Survey());
        Survey survey2 = surveyService.save(new Survey());
        Survey survey3 = surveyService.save(new Survey());
        Survey survey4 = surveyService.save(new Survey());
        Survey survey5 = surveyService.save(new Survey());

        // context
        Context context1 = new Context("Product Leadership", survey1);
        contextService.save(context1);

        Context context2 = new Context("Delivery Management", survey2);
        contextService.save(context2);

        Context context3 = new Context("Project Management", survey3);
        contextService.save(context3);

        Context context4 = new Context("Design Leadership", survey4);
        contextService.save(context4);

        Context context5 = new Context("Engineering Leadership", survey5);
        contextService.save(context5);

        /*******************************************************************/
        // questions

        Question question1a = new Question("What is the current priority?", true, QuestionType.TEXT, survey1);
        Question question1b = new Question("What is the current priority?", true, QuestionType.TEXT, survey2);
        Question question1c = new Question("What is the current priority?", true, QuestionType.TEXT, survey3);
        Question question1d = new Question("What is the current priority?", true, QuestionType.TEXT, survey4);
        Question question1e = new Question("What is the current priority?", true, QuestionType.TEXT, survey5);

        question1a = questionService.save(question1a);
        question1b = questionService.save(question1b);
        question1c = questionService.save(question1c);
        question1d = questionService.save(question1d);
        question1e = questionService.save(question1e);

        Question question2a = new Question("What Blockers are you working on?", true, QuestionType.TEXT, survey1);
        Question question2b = new Question("What Blockers are you working on?", true, QuestionType.TEXT, survey2);
        Question question2c = new Question("What Blockers are you working on?", true, QuestionType.TEXT, survey3);
        Question question2d = new Question("What Blockers are you working on?", true, QuestionType.TEXT, survey4);
        Question question2e = new Question("What Blockers are you working on?", true, QuestionType.TEXT, survey5);

        question2a = questionService.save(question2a);
        question2b = questionService.save(question2b);
        question2c = questionService.save(question2c);
        question2d = questionService.save(question2d);
        question2e = questionService.save(question2e);

        Question question3a = new Question("Are there any operational concerns the team should be aware of?", false, QuestionType.TEXT, survey1);
        Question question3b = new Question("Are there any operational concerns the team should be aware of?", false, QuestionType.TEXT, survey2);
        Question question3c = new Question("Are there any operational concerns the team should be aware of?", false, QuestionType.TEXT, survey3);
        Question question3d = new Question("Are there any operational concerns the team should be aware of?", false, QuestionType.TEXT, survey4);
        Question question3e = new Question("Are there any operational concerns the team should be aware of?", false, QuestionType.TEXT, survey5);

        question3a = questionService.save(question3a);
        question3b = questionService.save(question3b);
        question3c = questionService.save(question3c);
        question3d = questionService.save(question3d);
        question3e = questionService.save(question3e);

    }
}