package com.example.phonenotracker;

import java.util.ArrayList;

class SearchResponse {

    public ArrayList<MobileData> data;
    public String statusCode;

    String getStatusCode() {
        return statusCode;
    }

    ArrayList getData() {
        return data;
    }
}
