package com.library.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.services.entity.Book;
import com.library.services.entity.Borrower;
import com.library.services.serviceImpl.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("/borrowers")
    public ResponseEntity<Borrower> registerBorrower(@RequestBody Borrower borrower) {
        return ResponseEntity.ok(libraryService.registerBorrower(borrower));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> registerBook(@RequestBody Book book) {
        return ResponseEntity.ok(libraryService.registerBook(book));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(libraryService.getAllBooks());
    }

    @PostMapping("/borrowers/{borrowerId}/borrow/{bookId}")
    public ResponseEntity<Book> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.borrowBook(borrowerId, bookId));
    }

    @PostMapping("/borrowers/{borrowerId}/return/{bookId}")
    public ResponseEntity<Book> returnBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.returnBook(borrowerId, bookId));
    }
}

