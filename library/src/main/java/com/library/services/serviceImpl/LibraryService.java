package com.library.services.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.services.entity.Book;
import com.library.services.entity.Borrower;
import com.library.services.repository.BookRepository;
import com.library.services.repository.BorrowerRepository;

import jakarta.transaction.Transactional;

@Service
public class LibraryService {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookRepository bookRepository;

    public Borrower registerBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Book registerBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book borrowBook(Long borrowerId, Long bookId) {
        @SuppressWarnings("unused")
		Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new IllegalArgumentException("Borrower not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.isBorrowed()) {
            throw new IllegalStateException("Book already borrowed");
        }

        book.setBorrowed(true);
        return bookRepository.save(book);
    }

    @Transactional
    public Book returnBook(Long borrowerId, Long bookId) {
        @SuppressWarnings("unused")
		Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new IllegalArgumentException("Borrower not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (!book.isBorrowed()) {
            throw new IllegalStateException("Book is not borrowed");
        }

        book.setBorrowed(false);
        return bookRepository.save(book);
    }
}
