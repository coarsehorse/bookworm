package bookworm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book extends AuditModel {

    @Id
    @GeneratedValue(generator = "books_generator")
    @SequenceGenerator(
            name = "books_generator",
            sequenceName = "books_sequence"
    )
    private Long id;

    @NotBlank
    @Size(min = 1, max = 150)
    private String title;

    @NotBlank
    @Size(min = 1, max = 150)
    @Column(unique = true)
    private String titleLowC;

    private Integer pagesNum;

    @NotNull
    private Integer yearOfPublishing;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visitor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Visitor visitor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPagesNum() {
        return pagesNum;
    }

    public void setPagesNum(Integer pagesNum) {
        this.pagesNum = pagesNum;
    }

    public Integer getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(Integer yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public String getTitleLowC() {
        return titleLowC;
    }

    public void setTitleLowC(String titleLowC) {
        this.titleLowC = titleLowC;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pagesNum=" + pagesNum +
                ", yearOfPublishing=" + yearOfPublishing +
                ", visitor=" + visitor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                titleLowC.equals(book.titleLowC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titleLowC);
    }
}
