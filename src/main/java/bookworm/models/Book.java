package bookworm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

/**
 * Book domain model
 */
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

    @NotNull
    private Integer quantityInStock;

    private Integer pagesNum;

    @NotNull
    private Integer yearOfPublishing;

    @ManyToMany
    @JoinTable(
            name = "book_visitor",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "visitor_id"))
    @JsonIgnore
    private Set<Visitor> visitors;

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

    public String getTitleLowC() {
        return titleLowC;
    }

    public void setTitleLowC(String titleLowC) {
        this.titleLowC = titleLowC;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Set<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pagesNum=" + pagesNum +
                ", yearOfPublishing=" + yearOfPublishing +
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
