package com.example.SpringSecurity.controllers;

import com.example.SpringSecurity.models.Person;
import com.example.SpringSecurity.services.PersonDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private final PersonDetailsService personDetailsService;

    public PersonController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    @GetMapping()
    public String hello () {
        return "person/hello";
    }

    @GetMapping("/save")
    public String save (@ModelAttribute("person")Person person) {
        return "person/save";
    }
}
