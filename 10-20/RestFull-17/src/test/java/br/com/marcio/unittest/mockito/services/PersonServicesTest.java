package br.com.marcio.unittest.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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

import br.com.marcio.data.vo.v1.PersonVO;
import br.com.marcio.exceptions.RequiredObjectsNullException;
import br.com.marcio.models.Person;
import br.com.marcio.repositories.PersonRepository;
import br.com.marcio.services.PersonServices;
import br.com.marcio.unittest.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;
    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception{
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        
        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getkey());
        assertNotNull(result.getLinks());
        System.out.println(result.toString());
        assertTrue(result.toString().contains("[</person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @SuppressWarnings("null")
    @Test
    void testCreate() throws Exception{
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setkey(1L);

        when(repository.save(entity)).thenReturn(persisted);
        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getkey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("[</person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() throws Exception{
        Exception exception = assertThrows(RequiredObjectsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
 
    }
    @Test
    void testUpdateWithNullPerson() throws Exception{
        Exception exception = assertThrows(RequiredObjectsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
 
    }

    @Test
    void testUpdate() throws Exception{
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setkey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        lenient().when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getkey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("[</person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }


    @Test
    void testDelete() throws Exception{
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        service.delete(1L);
    }

    @Test
    void testFindAll() throws Exception{
        List<Person> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);
        
        var people = service.findAll();
        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);
        assertNotNull(personOne);
        assertNotNull(personOne.getkey());
        assertNotNull(personOne.getLinks());
        assertTrue(personOne.toString().contains("[</person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personFour = people.get(7);
        assertNotNull(personFour);
        assertNotNull(personFour.getkey());
        assertNotNull(personFour.getLinks());
        assertTrue(personFour.toString().contains("[</person/v1/7>;rel=\"self\"]"));
        assertEquals("Addres Test7", personFour.getAddress());
        assertEquals("First Name Test7", personFour.getFirstName());
        assertEquals("Last Name Test7", personFour.getLastName());
        assertEquals("Female", personFour.getGender());
    }

}
