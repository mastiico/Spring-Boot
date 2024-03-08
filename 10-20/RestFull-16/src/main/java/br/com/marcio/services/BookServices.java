package br.com.marcio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcio.controllers.BookController;
import br.com.marcio.data.vo.v1.BookVO;
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
        books.forEach(p -> {
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
        logger.info("Finding one personVO!");
        var entity = repository.findById(id).orElse(null);
        if(entity != null){
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;

        }else {
            throw new ResourceNotFoundException("No records found for this ID!");
        }
    }

    public BookVO update(BookVO book){
        logger.info("Update one personVO!");
        return book;
    }

    public BookVO create(BookVO book){
        logger.info("Create one personVO!");
        return book;
    }

    public BookVO delete(Long id){
        logger.info("Delete one personVO!");
        
        return null;
    }
}
