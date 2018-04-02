package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;

    @NotNull(message = "Login can not be empty")
    @NotEmpty(message = "Login can not be empty")
    @Email(message = "Email address must be well-formed")
    @Indexed(unique = true)
    private String login;

    @NotNull(message = "Name can not be empty")
    @NotEmpty(message = "Name can not be empty")
    @Length(min = 2, message = "Name must contain at least 2 characters")
    @Pattern(regexp = "^[A-Z].*", message = "Name should start with capital letter")
    private String name;

    @NotNull(message = "Surname can not be empty")
    @Length(min = 2, message = "Surname must contain at least 2 characters")
    @Pattern(regexp = "^[A-Z].*", message = "Surname should start with capital letter")
    private String surname;

    @Length(min = 60, max = 60)
    private String password;

    private boolean isActive;
    private Collection<String> roles;
    private Collection<TestAttempt> solvedTests;

    public Account(){}
    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", roles=" + roles +
                ", solvedTests=" + solvedTests +
                '}';
    }

  public Account(String login, String name, String surname, String password, boolean isActive, Collection<String> roles) {
    this.login = login;
    this.name = name;
    this.surname = surname;
    this.password = password;
    this.isActive = isActive;
    this.roles = roles;
  }

  public Account(String id, String login, String name, String surname, String password, boolean isActive, Collection<String> roles, Collection<TestAttempt> solvedTests) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
        this.solvedTests = solvedTests;
    }

    public Account(String login, String name, String surname, String password, boolean isActive, Collection<String> roles, Collection<TestAttempt> solvedTests) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
        this.solvedTests = solvedTests;
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
    public Collection<TestAttempt> getSolvedTests() { return solvedTests; }
    public void setSolvedTests(Collection<TestAttempt> solvedTests) { this.solvedTests = solvedTests; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
