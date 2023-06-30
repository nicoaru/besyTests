package com.asj.besyTest.controllers;

import com.asj.besyTest.model.entities.ChuckNorrisJoke;
import com.asj.besyTest.services.JokesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/jokes")
public class JokesController {

    private final JokesService jokesService;

    @GetMapping("/chuk-norris")
    public ResponseEntity<ChuckNorrisJoke> getRandomChuckNorrisJoke() {

        return ResponseEntity.status(HttpStatus.OK).body(jokesService.getRandomChuckNorrisJoke());
    }
}