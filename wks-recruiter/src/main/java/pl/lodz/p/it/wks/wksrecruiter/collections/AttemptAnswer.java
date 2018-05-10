package pl.lodz.p.it.wks.wksrecruiter.collections;

import java.util.Collection;

public class AttemptAnswer {
    private int questionNumber;
    private String question;
    private Collection<String> answers;
    private int maxPoints;
    private int score;

    public AttemptAnswer() {
    }

    public AttemptAnswer(int questionNumber, String question, Collection<String> answers) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.answers = answers;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Collection<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<String> answers) {
        this.answers = answers;
    }
}