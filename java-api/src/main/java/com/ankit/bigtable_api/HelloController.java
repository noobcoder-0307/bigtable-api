package com.ankit.bigtable_api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.HashMap;

@RestController
public class HelloController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/age/{name}")
    public String getAge(@PathVariable String name) {
    Map<String, Integer> ages = new HashMap<>();
    ages.put("Ankit", 34);
    ages.put("Priya", 28);

     if (!ages.containsKey(name)) {
            throw new PersonNotFoundException("Person not found: " + name);
        }

    return name + " is " + ages.get(name) + " years old";
}
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlePersonNotFound(PersonNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
}