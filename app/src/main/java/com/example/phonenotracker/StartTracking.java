package com.example.phonenotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;

public class StartTracking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tracking);
        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNumber();
            }
        });
    }

    public void searchNumber() {

        final EditText name = findViewById(R.id.Name);
        final EditText phNo = findViewById(R.id.Number);
        final EditText cnc_id =  findViewById(R.id.cnic);
        final EditText registration_Date =  findViewById(R.id.Registration_date);
        final EditText address = findViewById(R.id.Address);
        EditText search_phone_number =  findViewById(R.id.search_phone_number);
        String searchNumber = search_phone_number.getText().toString();
        if (searchNumber.startsWith("0")) {
            searchNumber = searchNumber.substring(1);
        }

        AndroidNetworking.get("https://x1fjm0edkf.execute-api.us-east-1.amazonaws.com/dev/telpak/search")
                .addQueryParameter("mobile", searchNumber)
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(SearchResponse.class, new ParsedRequestListener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse searchResponse) {
                        ArrayList list;
                        list = searchResponse.getData();
                        String code = searchResponse.getStatusCode();

                        if (!code.equals("200")) {
                            Toast.makeText(getApplicationContext(), "Record not found !", Toast.LENGTH_LONG).show();
                        } else {
                            if (list.size() > 0) {
                                MobileData result = (MobileData) list.get(0);
                                name.setText(result.getNAME());
                                phNo.setText(result.getMSISDN());
                                cnc_id.setText(result.getNIC());
                                registration_Date.setText(result.getSUB_DATE());
                                address.setText(result.getADDRESS());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Network not available", Toast.LENGTH_LONG).show();
                        AndroidNetworking.cancel(anError);
                    }
                });
    }

}
