package com.davidh.davidh.clinicapp;

/**
 * Created by davidh on 1/12/16.
 */
public class HistoryTemplate {

    private String title, body, letter, date;

    public HistoryTemplate(String title, String body, String letter, String date) {
        this.title = title;
        this.body = body;
        this.letter = letter;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLetter() {
        return letter.valueOf(letter.charAt(0));
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
