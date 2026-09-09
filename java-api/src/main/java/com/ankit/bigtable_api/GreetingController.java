package com.ankit.bigtable_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class GreetingController {

    @GetMapping ("/hello/{name}")
    public String greet(
        @PathVariable String name,
        @RequestParam(required = false, defaultValue = "Hello") String greeting) {
        return greeting + ", " + name + "!";
    }

}
