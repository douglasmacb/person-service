package com.criticalsoftware.personservice.service;

import com.criticalsoftware.personservice.controller.dto.PersonDTO;
import com.criticalsoftware.personservice.controller.form.PersonForm;
import com.criticalsoftware.personservice.enums.ErrorType;
import com.criticalsoftware.personservice.exception.BusinessException;
import com.criticalsoftware.personservice.model.Person;
import com.criticalsoftware.personservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public void createPerson(Person person) throws BusinessException {
        if(!repository.findByName(person.getName()).isPresent()) {
            repository.save(person);
        } else {
            throw new BusinessException(ErrorType.PERROR_001.getMessage(), ErrorType.PERROR_001.getLabel(), HttpStatus.CONFLICT);
        }
    }

    public List<PersonDTO> getPeople() {
        List<Person> people = repository.findAll();
        return people.stream().map(PersonDTO::new).collect(Collectors.toList());
    }

    public PersonDTO findPersonById(Long id) throws BusinessException {
        Optional<Person> person = repository.findById(id);
        if(person.isPresent()) {
            return new PersonDTO(person.get());
        }
        throw new BusinessException(ErrorType.PERROR_002.getMessage(), ErrorType.PERROR_002.getLabel(), HttpStatus.NOT_FOUND);
    }

    public void deletePersonById(Long id) throws BusinessException {
        Optional<Person> person = repository.findById(id);
        if(person.isPresent()) {
            repository.delete(person.get());
        } else {
            throw new BusinessException(ErrorType.PERROR_002.getMessage(), ErrorType.PERROR_002.getLabel(), HttpStatus.NOT_FOUND);
        }
    }

    public PersonDTO updatePerson(Long id, PersonForm form) throws BusinessException {
        Optional<Person> person = repository.findById(id);
        if(person.isPresent()) {
            Person updatedPerson = person.get();
            updatedPerson.setName(form.getName());
            updatedPerson.setNationality(form.getNationality());
            repository.save(updatedPerson);
            return new PersonDTO(updatedPerson);
        } else {
            throw new BusinessException(ErrorType.PERROR_002.getMessage(), ErrorType.PERROR_002.getLabel(), HttpStatus.NOT_FOUND);
        }
    }
}
