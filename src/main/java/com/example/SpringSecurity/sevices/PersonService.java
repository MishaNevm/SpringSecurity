package com.example.SpringSecurity.sevices;

import com.example.SpringSecurity.models.Person;
import com.example.SpringSecurity.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void save (Person person) {
        personRepository.save(person);
    }

    public Person findByName (String name) {
        return personRepository.findByName(name).orElse(null);
    }

}
