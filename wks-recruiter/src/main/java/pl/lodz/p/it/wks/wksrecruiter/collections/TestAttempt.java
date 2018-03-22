package pl.lodz.p.it.wks.wksrecruiter.collections;

import java.util.Collection;

public class TestAttempt {
    private String test_id;
    private int points;
    private Collection<AttemptAnswer> answers;

    public TestAttempt(){}
    public TestAttempt(String test_id, int points, Collection<AttemptAnswer> answers) {
        this.test_id = test_id;
        this.points = points;
        this.answers = answers;
    }

    public String getTest_id() { return test_id; }
    public void setTest_id(String test_id) { this.test_id = test_id; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public Collection<AttemptAnswer> getAnswers() { return answers; }
    public void setAnswers(Collection<AttemptAnswer> answers) { this.answers = answers; }

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
}
