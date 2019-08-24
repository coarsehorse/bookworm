package bookworm.controllers;

import bookworm.exceptions.ResourceNotFoundException;
import bookworm.models.Book;
import bookworm.repositories.BookRepository;
import bookworm.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "yearOfPublishing"));
    }

    @PostMapping("/visitors/{visitorId}/books")
    public List<Book> addBooks(@PathVariable Long visitorId, @Valid @RequestBody List<Book> books) {
        return visitorRepository.findById(visitorId)
                .map(visitor -> {
                    books.forEach(book -> {
                        book.setVisitor(visitor);
                        book.setTitleLowC(book.getTitle()
                                .toLowerCase()
                                .trim()
                                .replaceAll("\\s+", "_"));
                    });
                    return bookRepository.saveAll(books);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Visitor with id " + visitorId + " not found"));
    }
}
