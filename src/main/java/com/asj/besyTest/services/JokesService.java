package com.asj.besyTest.services;

import com.asj.besyTest.besyRefereneces.ReferencesServices;
import com.asj.besyTest.model.entities.ChuckNorrisJoke;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JokesService {
    private final ReferencesServices references;

    public ChuckNorrisJoke getRandomChuckNorrisJoke() {
        return references.webServiceChuckNorrisCall();
    }
}
