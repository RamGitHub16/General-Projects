package com.library.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.services.entity.Book;
import com.library.services.entity.Borrower;
import com.library.services.repository.BookRepository;
import com.library.services.repository.BorrowerRepository;
import com.library.services.serviceImpl.LibraryService;

@SpringBootTest
public class LibraryServiceTest {

    @InjectMocks
    private LibraryService libraryService;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterBorrower() {
        Borrower borrower = new Borrower();
        borrower.setName("John Doe");
        borrower.setEmail("john.doe@example.com");

        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        Borrower savedBorrower = libraryService.registerBorrower(borrower);

        verify(borrowerRepository, times(1)).save(borrower);
        assertEquals("John Doe", savedBorrower.getName());
        assertEquals("john.doe@example.com", savedBorrower.getEmail());
    }

    @Test
    public void testRegisterBook() {
        Book book = new Book();
        book.setIsbn("978-3-16-148410-0");
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = libraryService.registerBook(book);

        verify(bookRepository, times(1)).save(book);
        assertEquals("978-3-16-148410-0", savedBook.getIsbn());
        assertEquals("The Great Gatsby", savedBook.getTitle());
        assertEquals("F. Scott Fitzgerald", savedBook.getAuthor());
    }

    @Test
    public void testGetAllBooks() {
        libraryService.getAllBooks();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testBorrowBook() {
        Borrower borrower = new Borrower();
        borrower.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setBorrowed(false);

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book borrowedBook = libraryService.borrowBook(1L, 1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
        assertTrue(borrowedBook.isBorrowed());
    }

    @Test
    public void testReturnBook() {
        Borrower borrower = new Borrower();
        borrower.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setBorrowed(true);

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book returnedBook = libraryService.returnBook(1L, 1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
        assertFalse(returnedBook.isBorrowed());
    }
}
