package com.dahomeykid.jsontodb.controller;

import com.dahomeykid.jsontodb.entity.Book;
import com.dahomeykid.jsontodb.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/library")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<Book> fetchAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{title}")
    public Book fetchBookByTitle(@PathVariable("title") String title){
        Book found = bookService.getBookByTitle(title);
        return found;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book){
        Book created = bookService.saveBook(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{bookId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> fetchBookById(@PathVariable("bookId") String id){
        Optional<Book> found = bookService.getBookById(id);
        return ResponseEntity.of(found);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(
            @Valid @PathVariable("bookId") String id,
            @RequestBody Book updatedBook){
        Optional<Book> updated = bookService.updateBook(id, updatedBook);
        return updated
                .map(value -> ResponseEntity.ok().body(value))
                        .orElseGet(() -> {
                            Book created = bookService.saveBook(updatedBook);
                            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                    .path("/{bookId}")
                                    .buildAndExpand(created.getId())
                                    .toUri();

                            return ResponseEntity.created(location).body(created);
                        });
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> delete(@PathVariable("bookId") String id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return ResponseEntity.badRequest().body(map);
    }

}
