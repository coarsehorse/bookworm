package bookworm.controllers;

import bookworm.exceptions.NoBookInStockException;
import bookworm.exceptions.ResourceNotFoundException;
import bookworm.models.Book;
import bookworm.models.Visitor;
import bookworm.repositories.BookRepository;
import bookworm.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * Returns all existing books.
     *
     * @return All existing books.
     */
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "yearOfPublishing"));
    }

    /**
     * Add new books.
     *
     * @param books New books to add.
     * @return Newly created books with ids and other meta.
     */
    @PostMapping("/books")
    public List<Book> addBooks(@Valid @RequestBody List<Book> books) {
        books.forEach(book -> {
            book.setTitle(book.getTitle().trim());
            book.setTitleLowC(book.getTitle()
                    .toLowerCase()
                    .replaceAll("\\s+", "_"));
            book.setQuantityInStock(
                    book.getQuantityInStock() == null ? 1 : book.getQuantityInStock());
        });
        return bookRepository.saveAll(books);
    }

    /**
     * Get books of specified visitor.
     *
     * @param visitorId The visitor id.
     * @return Books of specified visitor.
     */
    @GetMapping("/visitors/{visitorId}/books")
    public Set<Book> getBooksByVisitor(@PathVariable Long visitorId) {
        return visitorRepository.findById(visitorId)
                .map(Visitor::getBooks)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Visitor with id " + visitorId + " is not found"));
    }

    /**
     * Map existing books with specified user.
     * If specified book is exists but not present in stock
     * <code>NoBookInStockException</code> exception will be thrown.
     *
     * @param visitorId The visitor id.
     * @param bookIds   The book ids to map.
     * @return Mapped books with updated meta.
     */
    @PostMapping("/visitors/{visitorId}/books")
    public List<Book> mapBooksToVisitor(@PathVariable Long visitorId,
                                        @Valid @RequestBody Set<Long> bookIds) {
        return visitorRepository
                .findById(visitorId)
                .map(visitor -> {
                    List<Book> addedBooks = bookIds.stream()
                            .map(bookId -> bookRepository
                                    .findById(bookId)
                                    .map(book -> {
                                        if (book.getQuantityInStock() < 1)
                                            throw new NoBookInStockException("No book \""
                                                    + book.getTitle() + "\" in stock");
                                        if (!visitor.getBooks().contains(book)) {
                                            book.setQuantityInStock(book.getQuantityInStock() - 1);
                                            book.getVisitors().add(visitor);
                                            visitor.getBooks().add(book);
                                            return bookRepository.save(book);
                                        }
                                        return book;
                                    })
                                    .orElseThrow(() ->
                                            new ResourceNotFoundException("Book with id " + bookId + " is not found")))
                            .collect(Collectors.toList());
                    visitorRepository.save(visitor);
                    return addedBooks;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Visitor with id " + visitorId + " is not found"));
    }
}
