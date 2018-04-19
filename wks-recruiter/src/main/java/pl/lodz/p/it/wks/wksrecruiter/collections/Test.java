package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.QuestionInfo;

import java.util.Collection;

@Document(collection = "tests")
public class Test {
    @Id
    private String id;
    @DBRef
    private Account author;
    private String name;
    private String language;
    private String description;
    private boolean isActive;
    private Collection<QuestionInfo> questions;
    private Collection<Position> positions;

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", description=" + description +
                ", questions=" + questions +
                ", positions=" + positions +
                '}';
    }
    public Test(){}

    public Test(String id, Account author, String name, String language, String description, boolean isActive, Collection<QuestionInfo> questions, Collection<Position> positions) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.language = language;
        this.description = description;
        this.isActive = isActive;
        this.questions = questions;
        this.positions = positions;
    }

    public Test(Account author, String name, String language, String description, boolean isActive, Collection<QuestionInfo> questions, Collection<Position> positions) {

        this.author = author;
        this.name = name;
        this.language = language;
        this.description = description;
        this.isActive = isActive;
        this.questions = questions;
        this.positions = positions;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Collection<QuestionInfo> getQuestions() { return questions; }
    public void setQuestions(Collection<QuestionInfo> questions) { this.questions = questions; }
    public Collection<Position> getPositions() { return positions; }
    public void setPositions(Collection<Position> positions) { this.positions = positions; }
    public Account getAuthor() { return author; }
    public void setAuthor(Account author) { this.author = author; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
