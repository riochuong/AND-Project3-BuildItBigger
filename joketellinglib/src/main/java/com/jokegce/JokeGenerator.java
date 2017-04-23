package com.jokegce;

import java.util.Random;

public class JokeGenerator {
    private static JokeGenerator INSTANCE = null;

    // array of all jokes
    private static final String JOKES [] = {
            " This app is a little Joke Generator !! Call it jokerator",
            " Some people just have a way with words, and other people … oh … not have way.",
            "All pro athletes are \u2028bilingual. They speak English and profanity.",
            "Escalators don’t break down… they just turn into stairs\n"
    };

    public static JokeGenerator getInstance(){
        if (INSTANCE == null){
            INSTANCE = new JokeGenerator();
        }
        return INSTANCE;
    }

    /**
     * pick a random jokes from the JOKES collection
     * @return
     */
    public String getRandomJoke(){
        int randomIndex = new Random().nextInt(JOKES.length);
        return JOKES[randomIndex];
    }

}
