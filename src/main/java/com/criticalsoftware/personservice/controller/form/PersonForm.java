package com.criticalsoftware.personservice.controller.form;

import com.criticalsoftware.personservice.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonForm {

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "nationality is required")
    private String nationality;

    public Person convert() {
        return new Person(this.name, this.nationality);
    }
}
