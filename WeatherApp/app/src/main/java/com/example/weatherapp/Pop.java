package com.example.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        Log.i("info","inside POP");

        DisplayMetrics dm = new DisplayMetrics();
        //getting the display size of the device
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels; //getting width of device
        int height = dm.heightPixels; //height

        //setting the size of our pop out window to 70%
        getWindow().setLayout((int)(width*0.7), (int)(height*0.5));

        TextView textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        ArrayList <String> str = intent.getStringArrayListExtra("message");
        String weather="";
        for (String i:str){
            weather += i+"\n";
        }
        textView.setText(weather);
    }
}
