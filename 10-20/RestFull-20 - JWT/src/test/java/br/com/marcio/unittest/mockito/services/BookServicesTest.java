package br.com.marcio.unittest.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.marcio.data.vo.v1.BookVO;
import br.com.marcio.exceptions.RequiredObjectsNullException;
import br.com.marcio.models.Book;
import br.com.marcio.repositories.BookRepository;
import br.com.marcio.services.BookServices;
import br.com.marcio.unittest.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        BookVO result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("[</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        LocalDate launchDate = result.getLaunchDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals("2024-03-15", launchDate.format(DateTimeFormatter.ISO_DATE));
        assertEquals(3.0, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }
	@Test
	void testCreate() throws Exception {
		Book entity = input.mockEntity(1); 
		entity.setId(1L);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
        assertTrue(result.toString().contains("[</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(3D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
	
    
    

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() throws Exception {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        lenient().when(repository.save(entity)).thenReturn(entity);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("[</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        LocalDate launchDate = result.getLaunchDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals("2024-03-15", launchDate.format(DateTimeFormatter.ISO_DATE));
        assertEquals(3.0, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}


    @Test
    void testFindAll() {
        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var collection = service.findAll();
        assertNotNull(collection);
        assertEquals(14, collection.size());

        var bookOne = collection.get(1);
        LocalDate launchDate = bookOne.getLaunchDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        assertTrue(bookOne.toString().contains("[</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", bookOne.getAuthor());
        assertEquals("Title Test1", bookOne.getTitle());
        assertEquals(3.0, bookOne.getPrice());
        assertEquals("2024-03-15", launchDate.format(DateTimeFormatter.ISO_DATE));

        var bookEight = collection.get(7);
        assertNotNull(bookEight);
        assertNotNull(bookEight.getKey());
        assertNotNull(bookEight.getLinks());
        assertTrue(bookEight.toString().contains("[</book/v1/7>;rel=\"self\"]"));
        assertEquals("Author Test7", bookEight.getAuthor());
        assertEquals("Title Test7", bookEight.getTitle());
        assertEquals(3.0, bookEight.getPrice());
        assertEquals("2024-03-15", launchDate.format(DateTimeFormatter.ISO_DATE));
    }
}
