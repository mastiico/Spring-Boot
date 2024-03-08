package br.com.marcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcio.data.vo.v1.BookVO;
import br.com.marcio.services.BookServices;
import br.com.marcio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/book/v1")
@Tag(name = "Book", description = "Endpoint for Managing Book")
public class BookController {

    @Autowired
    private BookServices service;

    @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Find's all Book", description = "Find's all books", tags = {"Book"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
					  content = {	
						@Content( mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = BookVO.class))  
						)
					}),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
    public List<BookVO> findAll(){
        return service.findAll();
    }




    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Find a Book", description = "Find a books", tags = {"Book"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
					  content = {	
						@Content( mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = BookVO.class))  
						)
					}),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
    public BookVO findById(@PathVariable("id") Long id) throws Exception{
        return service.findById(id);
    }

}
