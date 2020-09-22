# Apollo API

### Getting a JWT

once you have signed in through okta on the [client]("https://github.com/Lambda-School-Labs/Labs26-Apollo-FE-TeamB") do
```JS
let tokenObj = JSON.parse(localStorage.getItem("okta-token-storage"));
```
![Client Dev Console](./screenshots/img_1.PNG)
```JS
tokenObj.idToken.value
```
![Another Dev Console](./screenshots/img_2.PNG)

Ok so now that you have copied that idtoken property head over to postman, and in the auth tab select barer token and paste in the token.

![Postman](./screenshots/img_3.PNG)

Api will be extended

### Database layout

![Image of Database Layout](db.png)

### Endpoints:

## Topics

<details>

<summary>POST: http://apollo-b-api.herokuapp.com/topics/new - create a new topic</summary>

```JSON
{
    "title": "My New Topic",
    "owner": {
        "username": "admin"
    },
    "frequency": "WEEKLY",
    "defaultsurvey": {
        "questions": [
                    {
                        "body": "Do you have any blockers?",
                        "type": "TEXT",
                        "leader": true
                    },
                    {
                        "body": "What is the teams priority?",
                        "type": "TEXT",
                        "leader": true
                    },
                    {
                        "body": "How is your weekend?",
                        "type": "TEXT",
                        "leader": false
                    }
        ]
    }
}

```

</details>

<details>

<summary>GET: http://apollo-b-api.herokuapp.com/topics/topics - Get all topics regardless of user</summary>

GET Endpoint

```JSON
[
    {
        "topicId": 35,
        "title": "Topic 1",
        "owner": {
            "userid": 4,
            "username": "admin"
        },
        "frequency": "MONDAY",
        "defaultsurveyid": 34,
        "surveysrequests": [
            {
                "surveyId": 41
            },
            {
                "surveyId": 42
            },
            {
                "surveyId": 43
            },
            {
                "surveyId": 44
            },
            {
                "surveyId": 45
            }
        ],
        "users": [
            {
                "user": {
                    "userid": 4,
                    "username": "admin"
                }
            },
            {
                "user": {
                    "userid": 5,
                    "username": "cinnamon"
                }
            },
            {
                "user": {
                    "userid": 6,
                    "username": "barnbarn"
                }
            }
        ]
    },
    {
        "topicId": 36,
        "title": "Topic 2",
        "owner": {
            "userid": 4,
            "username": "admin"
        },
        "frequency": "MONDAY",
        "defaultsurveyid": 34,
        "surveysrequests": [],
        "users": []
    },
    {
        "topicId": 37,
        "title": "Topic 3",
        "owner": {
            "userid": 5,
            "username": "cinnamon"
        },
        "frequency": "WEEKLY",
        "defaultsurveyid": 34,
        "surveysrequests": [],
        "users": []
    },
    {
        "topicId": 38,
        "title": "Topic 4",
        "owner": {
            "userid": 5,
            "username": "cinnamon"
        },
        "frequency": "WEEKLY",
        "defaultsurveyid": 34,
        "surveysrequests": [],
        "users": []
    },
    {
        "topicId": 39,
        "title": "Topic 5",
        "owner": {
            "userid": 5,
            "username": "cinnamon"
        },
        "frequency": "MONTHLY",
        "defaultsurveyid": 34,
        "surveysrequests": [],
        "users": []
    },
    {
        "topicId": 40,
        "title": "Topic 6",
        "owner": {
            "userid": 5,
            "username": "cinnamon"
        },
        "frequency": "MONTHLY",
        "defaultsurveyid": 34,
        "surveysrequests": [],
        "users": []
    }
]
```

</details>

<details>

<summary>GET: http://apollo-b-api.herokuapp.com/users/users Returns user info included topics of which they are a member</summary>

```JSON
[
    {
        "userid": 4,
        "username": "admin",
        "ownedtopics": [
            {
                "topicId": 35,
                "title": "Topic 1",
                "frequency": "MONDAY",
                "defaultsurveyid": 34,
                "surveysrequests": [
                    {
                        "surveyId": 41
                    },
                    {
                        "surveyId": 42
                    },
                    {
                        "surveyId": 43
                    },
                    {
                        "surveyId": 44
                    },
                    {
                        "surveyId": 45
                    }
                ]
            },
            {
                "topicId": 36,
                "title": "Topic 2",
                "frequency": "MONDAY",
                "defaultsurveyid": 34,
                "surveysrequests": []
            }
        ],
        "topics": [
            {
                "topic": {
                    "topicId": 35,
                    "title": "Topic 1",
                    "owner": {
                        "userid": 4,
                        "username": "admin"
                    },
                    "frequency": "MONDAY",
                    "defaultsurveyid": 34,
                    "surveysrequests": [
                        {
                            "surveyId": 41
                        },
                        {
                            "surveyId": 42
                        },
                        {
                            "surveyId": 43
                        },
                        {
                            "surveyId": 44
                        },
                        {
                            "surveyId": 45
                        }
                    ]
                }
            }
        ]
    },
    {
        "userid": 5,
        "username": "cinnamon",
        "ownedtopics": [
            {
                "topicId": 37,
                "title": "Topic 3",
                "frequency": "WEEKLY",
                "defaultsurveyid": 34,
                "surveysrequests": []
            },
            {
                "topicId": 38,
                "title": "Topic 4",
                "frequency": "WEEKLY",
                "defaultsurveyid": 34,
                "surveysrequests": []
            },
            {
                "topicId": 39,
                "title": "Topic 5",
                "frequency": "MONTHLY",
                "defaultsurveyid": 34,
                "surveysrequests": []
            },
            {
                "topicId": 40,
                "title": "Topic 6",
                "frequency": "MONTHLY",
                "defaultsurveyid": 34,
                "surveysrequests": []
            }
        ],
        "topics": [
            {
                "topic": {
                    "topicId": 35,
                    "title": "Topic 1",
                    "owner": {
                        "userid": 4,
                        "username": "admin"
                    },
                    "frequency": "MONDAY",
                    "defaultsurveyid": 34,
                    "surveysrequests": [
                        {
                            "surveyId": 41
                        },
                        {
                            "surveyId": 42
                        },
                        {
                            "surveyId": 43
                        },
                        {
                            "surveyId": 44
                        },
                        {
                            "surveyId": 45
                        }
                    ]
                }
            }
        ]
    },
    {
        "userid": 6,
        "username": "barnbarn",
        "ownedtopics": [],
        "topics": [
            {
                "topic": {
                    "topicId": 35,
                    "title": "Topic 1",
                    "owner": {
                        "userid": 4,
                        "username": "admin"
                    },
                    "frequency": "MONDAY",
                    "defaultsurveyid": 34,
                    "surveysrequests": [
                        {
                            "surveyId": 41
                        },
                        {
                            "surveyId": 42
                        },
                        {
                            "surveyId": 43
                        },
                        {
                            "surveyId": 44
                        },
                        {
                            "surveyId": 45
                        }
                    ]
                }
            }
        ]
    }]

```

</details>

## Surveys
<details>
     
<summary>GET: http://apollo-b-api.herokuapp.com/surveys/all Returns list of surveys</summary>

```JSON
[
    {
        "surveyId": 34,
        "topic": null,
        "questions": []
    },
    {
        "surveyId": 41,
        "topic": {
            "topicId": 35,
            "title": "Topic 1",
            "frequency": "MONDAY"
        },
        "questions": [
            {
                "questionId": 51,
                "body": "Leader Question 1",
                "type": "TEXT",
                "leader": true
            },
            {
                "questionId": 52,
                "body": "Leader Question 2",
                "type": "TEXT",
                "leader": true
            },
            {
                "questionId": 53,
                "body": "Member Question 1",
                "type": "TEXT",
                "leader": false
            }
        ]
    },
    {
        "surveyId": 42,
        "topic": {
            "topicId": 35,
            "title": "Topic 1",
            "frequency": "MONDAY"
        },
        "questions": [
            {
                "questionId": 54,
                "body": "Member Question 2",
                "type": "TEXT",
                "leader": false
            },
            {
                "questionId": 55,
                "body": "Member Question 3",
                "type": "TEXT",
                "leader": false
            }
        ]
    },
    {
        "surveyId": 43,
        "topic": {
            "topicId": 35,
            "title": "Topic 1",
            "frequency": "MONDAY"
        },
        "questions": []
    },
    {
        "surveyId": 44,
        "topic": {
            "topicId": 35,
            "title": "Topic 1",
            "frequency": "MONDAY"
        },
        "questions": []
    },
    {
        "surveyId": 45,
        "topic": {
            "topicId": 35,
            "title": "Topic 1",
            "frequency": "MONDAY"
        },
        "questions": []
    }
]

```

</details>

## Questions
<details>

<summary>GET: http://apollo-b-api.herokuapp.com/questions Returns list of questions</summary>

```JSON
[
    {
        "questionId": 51,
        "body": "Leader Question 1",
        "type": "TEXT",
        "survey": {
            "surveyId": 41,
            "topic": {
                "topicId": 35,
                "title": "Topic 1",
                "frequency": "MONDAY"
            }
        },
        "leader": true
    },
    {
        "questionId": 52,
        "body": "Leader Question 2",
        "type": "TEXT",
        "survey": {
            "surveyId": 41,
            "topic": {
                "topicId": 35,
                "title": "Topic 1",
                "frequency": "MONDAY"
            }
        },
        "leader": true
    },
    {
        "questionId": 53,
        "body": "Member Question 1",
        "type": "TEXT",
        "survey": {
            "surveyId": 41,
            "topic": {
                "topicId": 35,
                "title": "Topic 1",
                "frequency": "MONDAY"
            }
        },
        "leader": false
    },
    {
        "questionId": 54,
        "body": "Member Question 2",
        "type": "TEXT",
        "survey": {
            "surveyId": 42,
            "topic": {
                "topicId": 35,
                "title": "Topic 1",
                "frequency": "MONDAY"
            }
        },
        "leader": false
    },
    {
        "questionId": 55,
        "body": "Member Question 3",
        "type": "TEXT",
        "survey": {
            "surveyId": 42,
            "topic": {
                "topicId": 35,
                "title": "Topic 1",
                "frequency": "MONDAY"
            }
        },
        "leader": false
    }
]

```

</details>
