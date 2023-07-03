package com.asj.besyTest.services;

import com.asj.besyTest.besyRefereneces.ReferencesServices;
import com.asj.besyTest.model.entities.ChuckNorrisJoke;
import com.asj.besyTest.model.entities.Emoji;
import com.asj.besyTest.model.entities.Quote;
import com.asj.besyTest.services.interfaces.JokesServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JokesService implements JokesServiceInterface {
    private final ReferencesServices references;

    public ChuckNorrisJoke getRandomChuckNorrisJoke() {
        return references.webServiceChuckNorrisCall();
    }

    public Emoji getEmoji() {
        return references.webServiceEmojiCall();
    }

    public Quote getQuote() {
        return references.quoteServiceEmojiCall();
    }
}
