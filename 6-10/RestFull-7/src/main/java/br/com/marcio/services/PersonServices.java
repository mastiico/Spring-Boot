package br.com.marcio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcio.exceptions.ResourceNotFoundException;
import br.com.marcio.models.Person;
import br.com.marcio.repositories.PersonRepository;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll(){
        logger.info("Finding all person!");

        return repository.findAll();
    }
    
    public Person findById(Long id) {
        logger.info("Finding one person!");
    
        return repository.findById(id).orElse(null);
    }
    

    public Person update(Person person) {
        logger.info("Updating one person!");
    
        var entity = repository.findById(person.getId()).orElse(null);
    
        if (entity != null) {
            entity.setFirstName(person.getFirstName());
            entity.setLastName(person.getLastName());
            entity.setAddress(person.getAddress());
            entity.setGender(person.getGender());
    
            return repository.save(entity);
        } else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }
    


    @SuppressWarnings("null")
    public Person create(Person person){
        logger.info("Creating one person!");

        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
    
        var entity = repository.findById(id).orElse(null);
    
        if (entity != null) {
            repository.delete(entity);
        } else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }
    
/** 
    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name" + i);
        person.setAddress("Some Adress in Brasil" + + i);
        person.setGender("Male");
        return person;
    }
*/
}
