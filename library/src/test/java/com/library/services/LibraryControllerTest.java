package com.library.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.services.controllers.LibraryController;
import com.library.services.entity.Book;
import com.library.services.entity.Borrower;
import com.library.services.serviceImpl.LibraryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
    }

    @Test
    public void testRegisterBorrower() throws Exception {
        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");
        borrower.setEmail("john.doe@example.com");

        when(libraryService.registerBorrower(any(Borrower.class))).thenReturn(borrower);

        mockMvc.perform(post("/api/borrowers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(borrower)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testRegisterBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("978-3-16-148410-0");
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");

        when(libraryService.registerBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-3-16-148410-0"))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(libraryService, times(1)).getAllBooks();
    }

    @Test
    public void testBorrowBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setBorrowed(true);

        when(libraryService.borrowBook(anyLong(), anyLong())).thenReturn(book);

        mockMvc.perform(post("/api/borrowers/1/borrow/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowed").value(true));
    }

    @Test
    public void testReturnBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setBorrowed(false);

        when(libraryService.returnBook(anyLong(), anyLong())).thenReturn(book);

        mockMvc.perform(post("/api/borrowers/1/return/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowed").value(false));
    }
}

