package pl.lodz.p.it.wks.wksrecruiter.collections;

import java.util.Collection;

public class QuestionInfo {
    private int questionNumber;
    private String questionPhrase;
    private Collection<Answer> answers;

    public QuestionInfo(){}
    public QuestionInfo(int questionNumber, String questionPhrase, Collection<Answer> answers) {
        this.questionNumber = questionNumber;
        this.questionPhrase = questionPhrase;
        this.answers = answers;
    }
    public int getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(int questionNumber) { this.questionNumber = questionNumber; }
    public String getQuestionPhrase() { return questionPhrase; }
    public void setQuestionPhrase(String questionPhrase) { this.questionPhrase = questionPhrase; }
    public Collection<Answer> getAnswers() { return answers; }
    public void setAnswers(Collection<Answer> answers) { this.answers = answers; }
}
