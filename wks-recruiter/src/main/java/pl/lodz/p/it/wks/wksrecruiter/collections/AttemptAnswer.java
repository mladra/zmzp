package pl.lodz.p.it.wks.wksrecruiter.collections;

public class AttemptAnswer {
    private int questionNumber;
    private String question;
    private String answer;

    public AttemptAnswer(){}

    public AttemptAnswer(int questionNumber, String question, String answer) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.answer = answer;
    }
    public int getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(int questionNumber) { this.questionNumber = questionNumber; }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}