package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "tests")
public class Test {
    @Id
    private String id;
    @DBRef
    private Account author;
    private String name;
    private int maxPoints;
    private String language;
    private boolean isActive;
    private Collection<QuestionInfo> questions;
    private Collection<Position> positions;

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", maxPoints=" + maxPoints +
                ", language='" + language + '\'' +
                ", questions=" + questions +
                ", positions=" + positions +
                '}';
    }
    public Test(){}

    public Test(String id, Account author, String name, int maxPoints, String language, boolean isActive, Collection<QuestionInfo> questions, Collection<Position> positions) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.maxPoints = maxPoints;
        this.language = language;
        this.isActive = isActive;
        this.questions = questions;
        this.positions = positions;
    }

    public Test(Account author, String name, int maxPoints, String language, boolean isActive, Collection<QuestionInfo> questions, Collection<Position> positions) {

        this.author = author;
        this.name = name;
        this.maxPoints = maxPoints;
        this.language = language;
        this.isActive = isActive;
        this.questions = questions;
        this.positions = positions;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getMaxPoints() { return maxPoints; }
    public void setMaxPoints(int maxPoints) { this.maxPoints = maxPoints; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Collection<QuestionInfo> getQuestions() { return questions; }
    public void setQuestions(Collection<QuestionInfo> questions) { this.questions = questions; }
    public Collection<Position> getPositions() { return positions; }
    public void setPositions(Collection<Position> positions) { this.positions = positions; }
    public Account getAuthor() { return author; }
    public void setAuthor(Account author) { this.author = author; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
