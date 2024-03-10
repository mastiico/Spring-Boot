package br.com.marcio.unittest.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.marcio.models.Book;
import br.com.marcio.models.Person;
import br.com.marcio.repositories.BookRepository;
import br.com.marcio.services.BookServices;
import br.com.marcio.unittest.mapper.mocks.MockBook;

@ExtendWith(MockitoExtension.class)
public class BookServicesTest {

    
    MockBook input;

    @InjectMocks
    private BookServices service;
    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        Book entity = input.mockEntity(1);
        entity.setId(1L);
    
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
    
        LocalDate expectedDate = LocalDate.of(2024, 3, 15);
    
        Book result = service.findById();
    
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result.toString());
        assertTrue(result.toString().contains("[</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test 1", result.getAuthor());
        assertEquals(expectedDate, result.getLaunchDate().toLocalDate()); // Comparação usando LocalDate
        assertEquals(3.0, result.getPrice());
        assertEquals("Title Test 1", result.getTitle());
    }
    

}
