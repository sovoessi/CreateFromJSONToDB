package com.dahomeykid.jsontodb;

import com.dahomeykid.jsontodb.entity.Book;
import com.dahomeykid.jsontodb.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class JsonToDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonToDbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(BookService bookService) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Book>> typeReference = new TypeReference<>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json");
            try {
                List<Book> books = mapper.readValue(inputStream, typeReference);
//                System.out.println(users);
                bookService.saveAll(books);
                System.out.println("Books Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save books: " + e.getMessage());
            }
        };
    }
}
