package br.com.marcio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcio.data.vo.v1.PersonVO;
import br.com.marcio.data.vo.v2.PersonVOV2;
import br.com.marcio.exceptions.ResourceNotFoundException;
import br.com.marcio.mapper.DozerMapper;
import br.com.marcio.mapper.custom.PersonMapper;
import br.com.marcio.models.Person;
import br.com.marcio.repositories.PersonRepository;

@SuppressWarnings("null")
@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll(){
        logger.info("Finding all person!");

        return DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
    }
    
    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
    
        var entity = repository.findById(id).orElse(null);
        return DozerMapper.parseObject(entity, PersonVO.class);
    }
    
    public PersonVO update(PersonVO personVO) {
        logger.info("Updating one person!");
    
        var entity = repository.findById(personVO.getId()).orElse(null);
    
        if (entity != null) {
            entity.setFirstName(personVO.getFirstName());
            entity.setLastName(personVO.getLastName());
            entity.setAddress(personVO.getAddress());
            entity.setGender(personVO.getGender());
    
            var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
            return vo;            
        } else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }

    public PersonVO create(PersonVO person){
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
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

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person with v2!");
        var entity = mapper.convertVoToEntity(person);
        var vo = mapper.convertEntityToVO(repository.save(entity));
        return vo;
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
