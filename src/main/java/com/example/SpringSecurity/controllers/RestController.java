package com.example.SpringSecurity.controllers;


import com.example.SpringSecurity.DTO.PersonDTO;
import com.example.SpringSecurity.models.Person;
import com.example.SpringSecurity.sevices.PersonService;
import com.example.SpringSecurity.util.PersonErrorResponse;
import com.example.SpringSecurity.util.PersonNotCreatedException;
import com.example.SpringSecurity.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.findAll().stream().map(this::convertToPersonDTO).toList();
    }
    @GetMapping("/{id}")
    public PersonDTO findOne (@PathVariable("id") int id) {
        return convertToPersonDTO(personService.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError fieldError : errors) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(errorMessage.toString());
        }
        personService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException exception) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse
                ("Person not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO (Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
