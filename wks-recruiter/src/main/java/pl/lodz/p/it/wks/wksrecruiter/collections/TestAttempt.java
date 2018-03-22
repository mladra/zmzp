package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collection;

public class TestAttempt {
    @DBRef
    private Test test_id;
    private int points;
    private Collection<AttemptAnswer> answers;

    public TestAttempt(){}
    public TestAttempt(Test test_id, int points, Collection<AttemptAnswer> answers) {
        this.test_id = test_id;
        this.points = points;
        this.answers = answers;
    }

    public Test getTest_id() { return test_id; }
    public void setTest_id(Test test_id) { this.test_id = test_id; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public Collection<AttemptAnswer> getAnswers() { return answers; }
    public void setAnswers(Collection<AttemptAnswer> answers) { this.answers = answers; }
}
