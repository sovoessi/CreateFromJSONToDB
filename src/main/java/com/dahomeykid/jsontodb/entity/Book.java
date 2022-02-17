package com.dahomeykid.jsontodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Document(collection = "library")
@AllArgsConstructor
public class Book {

    @Id
    private String id;

    @NotNull(message = "Title is required")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "Title must be a string")
    private String title;

    @Pattern(regexp="^[a-zA-Z ]+$", message = "Path must be a string")
    private String path;

    public Book() {
    }

    public Book(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public Book updateWith(Book book) {
        return new Book(
                book.title,
                book.path
        );
    }


}
