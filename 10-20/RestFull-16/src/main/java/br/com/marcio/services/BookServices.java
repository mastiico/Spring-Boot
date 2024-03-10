package br.com.marcio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcio.controllers.BookController;
import br.com.marcio.data.vo.v1.BookVO;
import br.com.marcio.exceptions.RequiredObjectsNullException;
import br.com.marcio.exceptions.ResourceNotFoundException;
import br.com.marcio.mapper.DozerMapper;
import br.com.marcio.repositories.BookRepository;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    @SuppressWarnings("null")
    public List<BookVO> findAll() {
        logger.info("Finding all books!");
    
        var books = DozerMapper.parseListBook(repository.findAll(), BookVO.class);
        books
            .stream()
            .forEach(p -> {
                try {
                    p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
                } catch (Exception e) {
                    logger.info("Error while adding self link to BookVO: {}");
                    e.printStackTrace();
                }
            });
        return books;
    }
    

    @SuppressWarnings("null")
    public BookVO findById(Long id) throws Exception{
        logger.info("Finding one Boke!");
        var entity = repository.findById(id).orElse(null);
        if(entity != null){
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;

        }else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }

    @SuppressWarnings("null")
    public BookVO update(BookVO book) throws Exception{
        logger.info("Update one Book!");
        if(book == null) throw new RequiredObjectsNullException();
    
        var entity = repository.findById(book.getKey()).orElse(null);
    
        if (entity != null) {
            entity.setAuthor(book.getAuthor());
            entity.setLaunchDate(book.getLaunchDate());
            entity.setPrice(book.getPrice());
            entity.setTitle(book.getTitle());
    
            var vo = DozerMapper.parseObject(entity, BookVO.class);
            vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
            return vo;            
        } else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }

    @SuppressWarnings("null")
    public BookVO create(BookVO book) throws Exception{
        logger.info("Create one Book!");
        if(book == null) throw new RequiredObjectsNullException();
        var entity = repository.findById(book.getKey()).orElse(null);
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO delete(Long id){
        logger.info("Delete one Book!");
        
        return null;
    }
}
