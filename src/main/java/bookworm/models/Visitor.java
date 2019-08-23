package bookworm.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}

