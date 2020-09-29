package com.lambdaschool.apollo.views;

public class SurveyQuestion {
	private String body;
	private QuestionType type;
	private Boolean leader;
	private String answer;

	public SurveyQuestion() {
	}

	public SurveyQuestion(String body, QuestionType type, Boolean leader) {
		this.body = body;
		this.type = type;
		this.leader = leader;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public Boolean getLeader() {
		return leader;
	}

	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "SurveyQuestion{" +
				   "body='" + body + '\'' +
				   ", type=" + type +
				   ", leader=" + leader +
				   ", answer='" + answer + '\'' +
				   '}';
	}
}
