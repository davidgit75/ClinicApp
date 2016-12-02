package com.davidh.davidh.clinicapp;

import android.support.v7.widget.CardView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by davidh on 1/12/16.
 */
public class HistoryTemplate {

    private String title, body, letter, date;
    private JSONObject data;

    public HistoryTemplate(String title, String body, String letter, String date, JSONObject data) {
        this.title = title;
        this.body = body;
        this.letter = letter;
        this.date = date;
        this.data = data;
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
        Date cDate = new Date();
        int year = cDate.getYear();
        int month = cDate.getMonth();
        int day = cDate.getDate();
        Long time = new Date(year, month, day).getTime();

        Date tmpTime = new Date(Long.parseLong(this.date));
        int thisYear = tmpTime.getYear();
        int thisMonth = tmpTime.getMonth();
        int thisDay = tmpTime.getDate();
        Long thisTime = new Date(thisYear, thisMonth, thisDay).getTime();


        SimpleDateFormat formatter;

        if(time.equals(thisTime)){
             formatter = new SimpleDateFormat("hh:mm:ss");
        }else{
            formatter = new SimpleDateFormat("dd/MM/yy");
        }

        String folderName = formatter.format(tmpTime);

        return folderName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }


    /*public HistoryTemplate(String title, String body, String letter, String date) {
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
    }*/
}
