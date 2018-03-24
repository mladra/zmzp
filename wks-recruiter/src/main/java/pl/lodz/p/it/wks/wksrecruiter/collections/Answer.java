package pl.lodz.p.it.wks.wksrecruiter.collections;

public class Answer {
    private String answerType;
    private String answerValue;
    private boolean correctAnswer;
    private int points;

    public Answer(){}
    public Answer(String answerType, String answerValue, boolean correctAnswer, int points) {
        this.answerType = answerType;
        this.answerValue = answerValue;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }
    public String getAnswerType() { return answerType; }
    public void setAnswerType(String answerType) { this.answerType = answerType; }
    public String getAnswerValue() { return answerValue; }
    public void setAnswerValue(String answerValue) { this.answerValue = answerValue; }
    public boolean isCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(boolean correctAnswer) { this.correctAnswer = correctAnswer; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
