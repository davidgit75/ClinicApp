package com.davidh.davidh.clinicapp;

import org.json.JSONObject;

/**
 * Created by davidh on 2/12/16.
 */
public class DataHistory {

    private JSONObject data;

    public DataHistory(JSONObject data) {
        this.data = data;
    }

    public JSONObject getData() {
        return data;
    }
}
