package com.dahomeykid.jsontodb.repository;

import com.dahomeykid.jsontodb.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book,String > {

    @Query("{title:'?0'}")
    Book findBookByTitle(String title);
}
