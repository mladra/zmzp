package pl.lodz.p.it.wks.wksrecruiter.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "invalidTokens")
public class InvalidToken {

    @Id
    private String id;

    @NotNull
    @NotEmpty
    @Indexed(unique = true)
    private String value;

    public InvalidToken() {}

    public InvalidToken(@NotNull @NotEmpty String value) {
        this.value = value;
    }

    public InvalidToken(String id, @NotNull @NotEmpty String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "InvalidToken{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
