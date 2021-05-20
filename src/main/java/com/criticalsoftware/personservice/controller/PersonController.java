package com.criticalsoftware.personservice.controller;

import com.criticalsoftware.personservice.controller.dto.ErrorResponseDTO;
import com.criticalsoftware.personservice.controller.dto.PersonDTO;
import com.criticalsoftware.personservice.controller.form.PersonForm;
import com.criticalsoftware.personservice.exception.BusinessException;
import com.criticalsoftware.personservice.model.Person;
import com.criticalsoftware.personservice.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api(value = "/person", tags = "person")
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "Create a new person")
    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody @Valid PersonForm form, UriComponentsBuilder builder) {
        try {
            Person person = form.convert();
            personService.createPerson(person);
            URI uri = builder.path("/person/{id}").buildAndExpand(person.getId()).toUri();
            return ResponseEntity.created(uri).body(new PersonDTO(person));
        } catch(BusinessException e) {
            return ResponseEntity.status(e.getHttpStatusCode()).body(new ErrorResponseDTO(e.getMessage(), e.getError()));
        }
    }

    @ApiOperation(value = "Update a person")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Long id, @RequestBody @Valid PersonForm form) {
        try {
            PersonDTO updatedPerson = personService.updatePerson(id, form);
            return ResponseEntity.ok().body(updatedPerson);
        } catch(BusinessException e) {
            return ResponseEntity.status(e.getHttpStatusCode()).body(new ErrorResponseDTO(e.getMessage(), e.getError()));
        }
    }

    @ApiOperation(value = "List all people")
    @GetMapping
    public ResponseEntity<?> getPeople() {
        return ResponseEntity.ok().body(personService.getPeople());
    }

    @ApiOperation(value = "Find a person")
    @GetMapping("/{id}")
    public ResponseEntity<?> findPersonById(@PathVariable Long id) {
        try {
            PersonDTO person = personService.findPersonById(id);
            return ResponseEntity.ok().body(person);
        } catch(BusinessException e) {
            return ResponseEntity.status(e.getHttpStatusCode()).body(new ErrorResponseDTO(e.getMessage(), e.getError()));
        }
    }

    @ApiOperation(value = "Delete a person")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonById(@PathVariable Long id) {
        try {
            personService.deletePersonById(id);
            return ResponseEntity.ok().build();
        } catch(BusinessException e) {
            return ResponseEntity.status(e.getHttpStatusCode()).body(new ErrorResponseDTO(e.getMessage(), e.getError()));
        }
    }
}
