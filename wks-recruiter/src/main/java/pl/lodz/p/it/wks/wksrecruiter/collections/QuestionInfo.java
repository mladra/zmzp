package pl.lodz.p.it.wks.wksrecruiter.collections;

import java.util.Collection;

public class QuestionInfo {
    private int question_number;
    private String question_phrase;
    private Collection<Answer> answers;

    public QuestionInfo(){}
    public QuestionInfo(int question_number, String question_phrase, Collection<Answer> answers) {
        this.question_number = question_number;
        this.question_phrase = question_phrase;
        this.answers = answers;
    }
    public int getQuestion_number() { return question_number; }
    public void setQuestion_number(int question_number) { this.question_number = question_number; }
    public String getQuestion_phrase() { return question_phrase; }
    public void setQuestion_phrase(String question_phrase) { this.question_phrase = question_phrase; }
    public Collection<Answer> getAnswers() { return answers; }
    public void setAnswers(Collection<Answer> answers) { this.answers = answers; }
}
