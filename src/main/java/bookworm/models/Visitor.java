package bookworm.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "visitors")
public class Visitor extends AuditModel {

    @Id
    @GeneratedValue(generator = "visitors_generator")
    @SequenceGenerator(
            name = "visitors_generator",
            sequenceName = "visitors_sequence"
    )
    private Long id;

    @NotBlank
    @Size(min = 5, max = 150)
    private String fullName;

    @NotBlank
    @Size(min = 5, max = 150)
    @Column(unique = true)
    private String fullNameLowC;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameLowC() {
        return fullNameLowC;
    }

    public void setFullNameLowC(String fullNameLowC) {
        this.fullNameLowC = fullNameLowC;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return id.equals(visitor.id) &&
                fullNameLowC.equals(visitor.fullNameLowC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullNameLowC);
    }
}

