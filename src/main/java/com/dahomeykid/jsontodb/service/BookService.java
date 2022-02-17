package com.dahomeykid.jsontodb.service;

import com.dahomeykid.jsontodb.entity.Book;
import com.dahomeykid.jsontodb.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks(){return  bookRepository.findAll();}

    public Optional<Book> getBookById(String id){return bookRepository.findById(id);}

    public Book getBookByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }

    public Book saveBook(Book book){ return bookRepository.save(book);}

    public List<Book> saveAll(List<Book> books){ return bookRepository.saveAll(books);}

    public Optional<Book> updateBook(String id, Book newBook){
        return bookRepository.findById(id)
                .map(oldBook -> {
                    Book updated = oldBook.updateWith(newBook);
                    return bookRepository.save(updated);
                });
    }

    public void delete(String bookId) {bookRepository.deleteById(bookId);}
}