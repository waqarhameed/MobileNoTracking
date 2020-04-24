package com.example.phonenotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.text.NumberFormat;
import java.util.Locale;

import static com.example.phonenotracker.R.layout.activity_main;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView dbName;
    private TextView dbStatus;
    private TextView totalRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);


        dbName = findViewById(R.id.database_name);
        dbStatus = findViewById(R.id.database_status);
        totalRecords = findViewById(R.id.Total_Records);
        AndroidNetworking.get("https://x1fjm0edkf.execute-api.us-east-1.amazonaws.com/dev/telpak/status")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(LiveStatus.class, new ParsedRequestListener<LiveStatus>() {
                    @Override
                    public void onResponse(LiveStatus t) {
                        String databaseName = t.getDatabaseName();
                        String databaseStatus = t.getDatabaseStatus();
                        String totalRec = t.getTotalRecords();
                        int number = Integer.parseInt(totalRec);
                        NumberFormat numFmt = NumberFormat.getInstance(new Locale("pk", "PK"));
                        String totalNum = numFmt.format(number);
                        dbName.setText(databaseName);
                        dbStatus.setText(databaseStatus);
                        totalRecords.setText(totalNum);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Network not available", Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void lunchApp(View view) {
        Intent intent = new Intent(this, StartTracking.class);
        startActivity(intent);
    }


}
