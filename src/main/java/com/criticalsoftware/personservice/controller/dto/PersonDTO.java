package com.criticalsoftware.personservice.controller.dto;

import com.criticalsoftware.personservice.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;
    private String name;
    private String nationality;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.nationality = person.getNationality();
    }
}
