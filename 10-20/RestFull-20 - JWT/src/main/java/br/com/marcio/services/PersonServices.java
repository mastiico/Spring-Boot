package br.com.marcio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.marcio.controllers.PersonController;
import br.com.marcio.data.vo.v1.PersonVO;
import br.com.marcio.exceptions.RequiredObjectsNullException;
import br.com.marcio.exceptions.ResourceNotFoundException;
import br.com.marcio.mapper.DozerMapper;
import br.com.marcio.models.Person;
import br.com.marcio.repositories.PersonRepository;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;
    
    @SuppressWarnings("null")
    public List<PersonVO> findAll(){
        logger.info("Finding all personVO!");

        List<PersonVO> persons = DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
        persons
            .stream()
            .forEach(p -> {
                try {
                    p.add(linkTo(methodOn(PersonController.class).findById(p.getkey())).withSelfRel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        return persons;
    }
    
    @SuppressWarnings("null")
    public PersonVO findById(Long id) throws Exception {
        logger.info("Finding one personVO!");
        
        
        var entity = repository.findById(id).orElse(null);
        if(entity != null){
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;

        }else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }
    
    @SuppressWarnings("null")
    public PersonVO update(PersonVO person) throws Exception {
        if(person == null) throw new RequiredObjectsNullException();
        logger.info("Updating one personVO!");
    
        var entity = repository.findById(person.getkey()).orElse(null);
    
        if (entity != null) {
            entity.setFirstName(person.getFirstName());
            entity.setLastName(person.getLastName());
            entity.setAddress(person.getAddress());
            entity.setGender(person.getGender());
    
            var vo = DozerMapper.parseObject(entity, PersonVO.class);
            vo.add(linkTo(methodOn(PersonController.class).findById(vo.getkey())).withSelfRel());
            return vo;            
        } else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }

    @SuppressWarnings("null")
    public PersonVO create(PersonVO person) throws Exception{
        if(person == null) throw new RequiredObjectsNullException();
        logger.info("Creating one personVO!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getkey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one personVO!");
    
        @SuppressWarnings("null")
        var entity = repository.findById(id).orElse(null);
    
        if (entity != null) {
            repository.delete(entity);
        } else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }
    
/** 
    private PersonVO mockPersonVO(int i) {
        PersonVO personVO = new PersonVO();
        personVO.setId(counter.incrementAndGet());
        personVO.setFirstName("PersonVO name " + i);
        personVO.setLastName("Last name" + i);
        personVO.setAddress("Some Adress in Brasil" + + i);
        personVO.setGender("Male");
        return personVO;
    }
*/
}
