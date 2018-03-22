package pl.lodz.p.it.wks.wksrecruiter.collections;

public class Answer {
    private String answer_type;
    private String answer_value;
    private boolean correct_answer;
    private int points;

    public Answer(){}
    public Answer(String answer_type, String answer_value, boolean correct_answer, int points) {
        this.answer_type = answer_type;
        this.answer_value = answer_value;
        this.correct_answer = correct_answer;
        this.points = points;
    }
    public String getAnswer_type() { return answer_type; }
    public void setAnswer_type(String answer_type) { this.answer_type = answer_type; }
    public String getAnswer_value() { return answer_value; }
    public void setAnswer_value(String answer_value) { this.answer_value = answer_value; }
    public boolean isCorrect_answer() { return correct_answer; }
    public void setCorrect_answer(boolean correct_answer) { this.correct_answer = correct_answer; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
