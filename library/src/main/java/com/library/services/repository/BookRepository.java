package com.library.services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.services.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsbn(String isbn);
}
