package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collection;

public class TestAttempt {
    @DBRef
    private Test testId;
    private int points;
    private Collection<AttemptAnswer> answers;
    public TestAttempt(){}
    public TestAttempt(Test testId, int points, Collection<AttemptAnswer> answers) {
        this.testId = testId;
        this.points = points;
        this.answers = answers;
    }
    public Test getTestId() { return testId; }
    public void setTestId(Test testId) { this.testId = testId; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public Collection<AttemptAnswer> getAnswers() { return answers; }
    public void setAnswers(Collection<AttemptAnswer> answers) { this.answers = answers; }
}
