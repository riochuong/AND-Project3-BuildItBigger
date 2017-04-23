package com.jokegce.chuondao.myapplication.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class JokeBean {

    private String content;

    public String getJoke() {
        return content;
    }

    public void setJoke(String data) {
        content = data;
    }
}