package pl.lodz.p.it.wks.wksrecruiter.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collection;
import java.util.Objects;

public class TestAttempt {

    public static final int SOLVED_UNCHECKED = -1;

    @DBRef
    private Test test;
    private int maxPoints;
    private int score;

    private Collection<AttemptAnswer> answers;

    public TestAttempt() {
    }

    public TestAttempt(int maxPoints, int score, Collection<AttemptAnswer> answers) {
        this.maxPoints = maxPoints;
        this.answers = answers;
        this.score = score;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Collection<AttemptAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<AttemptAnswer> answers) {
        this.answers = answers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestAttempt that = (TestAttempt) o;
        return maxPoints == that.maxPoints &&
                Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(maxPoints, answers);
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
