package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "tests")
public class Test {
    @Id
    private String id;
    private String name;
    private int max_points;
    private String language;
    private Collection<QuestionInfo> questions;
    private Collection<String> position;

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", max_points=" + max_points +
                ", language='" + language + '\'' +
                ", questions=" + questions +
                ", position=" + position +
                '}';
    }
    public Test(){}
    public Test(String name, int max_points, String language, Collection<QuestionInfo> questions, Collection<String> position) {
        this.name = name;
        this.max_points = max_points;
        this.language = language;
        this.questions = questions;
        this.position = position;
    }

    public Test(String id, String name, int max_points, String language, Collection<QuestionInfo> questions, Collection<String> position) {
        this.id = id;
        this.name = name;
        this.max_points = max_points;
        this.language = language;
        this.questions = questions;
        this.position = position;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getMax_points() { return max_points; }
    public void setMax_points(int max_points) { this.max_points = max_points; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Collection<QuestionInfo> getQuestions() { return questions; }
    public void setQuestions(Collection<QuestionInfo> questions) { this.questions = questions; }
    public Collection<String> getPosition() { return position; }
    public void setPosition(Collection<String> position) { this.position = position; }
}
