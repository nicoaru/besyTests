package com.asj.besyTest.services.interfaces;

import com.asj.besyTest.model.entities.ChuckNorrisJoke;
import com.asj.besyTest.model.entities.Emoji;
import com.asj.besyTest.model.entities.Quote;

public interface JokesServiceInterface {
    ChuckNorrisJoke getRandomChuckNorrisJoke();
    Emoji getEmoji();
    Quote getQuote();
}
