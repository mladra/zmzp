package pl.lodz.p.it.wks.wksrecruiter.collections;

public class AttemptAnswer {
    private int question_number;
    private String answer;

    public AttemptAnswer(){}
    public AttemptAnswer(int question_number, String answer) {
        this.question_number = question_number;
        this.answer = answer;
    }
    public int getQuestion_number() { return question_number; }
    public void setQuestion_number(int question_number) { this.question_number = question_number; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}