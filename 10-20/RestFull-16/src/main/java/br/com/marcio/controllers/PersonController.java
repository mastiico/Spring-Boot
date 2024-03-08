package br.com.marcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.marcio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcio.data.vo.v1.PersonVO;
import br.com.marcio.services.PersonServices;

@RestController
@RequestMapping("/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

	@Autowired
	private PersonServices service;

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Find's all people", description = "Find's all people", tags = {"People"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
					  content = {	
						@Content( mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))  
						)
					}),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
		public List<PersonVO> findAll() {
		return service.findAll();
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
	@Operation(summary = "Find a person", description = "Find a person", tags = {"People"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",content = @Content( schema = @Schema(implementation = PersonVO.class))),
		@ApiResponse(description = "No content", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
		public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {
		return service.findById(id);
	}


	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
				 produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Create a person", description = "Create a person", tags = {"People"}, 
				 responses = {
					 @ApiResponse(description = "Success", responseCode = "200",content = @Content( schema = @Schema(implementation = PersonVO.class))),
					 @ApiResponse(description = "No content", responseCode = "400", content = @Content),
					 @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					 @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					 @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
				 })
		public PersonVO create(@RequestBody PersonVO person) throws Exception {
		return service.create(person);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Update a person", description = "Update a person", tags = {"People"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",content = @Content( schema = @Schema(implementation = PersonVO.class))),
		@ApiResponse(description = "No content", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
		public PersonVO update(@RequestBody PersonVO person) throws Exception {
		return service.update(person);
	}

	@DeleteMapping(value ="/{id}")
	@Operation(summary = "Delete a person", description = "Delete a person", tags = {"People"}, 
	responses = {
		@ApiResponse(description = "No content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
		public ResponseEntity<?> delete(@PathVariable(value= "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
