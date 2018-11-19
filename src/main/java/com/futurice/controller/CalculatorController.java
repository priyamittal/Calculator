package com.futurice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class CalculatorController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(name= "/calculate" , method = GET, produces = "application/json")
    public ResponseEntity<Object> calculate(@RequestParam(value = "query") String base64Query) throws UnsupportedEncodingException {
        final String query = decodeBase64Query(base64Query);
        return ResponseEntity.ok(query);
    }

    private String decodeBase64Query(@RequestParam(value = "query") String base64Query) throws UnsupportedEncodingException {
        return  new String(Base64.getDecoder().decode(base64Query), "utf-8");
    }

}
