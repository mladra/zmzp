package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String login;
    private String name;
    private String surname;
    private String password;
    private Collection<String> roles;

    public Account(){}
    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", roles=" + roles +
                ", solved_tests=" + solved_tests +
                '}';
    }

    public Account(String id, String login, String name, String surname, String password, Collection<String> roles, Collection<TestAttempt> solved_tests) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.roles = roles;
        this.solved_tests = solved_tests;
    }

    public Account(String login, String name, String surname, String password, Collection<String> roles, Collection<TestAttempt> solved_tests) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.roles = roles;
        this.solved_tests = solved_tests;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Collection<String> getRoles() { return roles; }
    public void setRoles(Collection<String> roles) { this.roles = roles; }
    public Collection<TestAttempt> getSolved_tests() { return solved_tests; }
    public void setSolved_tests(Collection<TestAttempt> solved_tests) { this.solved_tests = solved_tests; }
    private Collection<TestAttempt> solved_tests;
}
