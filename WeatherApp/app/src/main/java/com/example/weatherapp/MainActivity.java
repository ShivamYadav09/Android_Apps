package com.example.weatherapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText cityName;

    public void findWeather(View view){
        Log.i("cityName", cityName.getText().toString());

        //hide the keyboard after the user has pressed the button
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        String API_KEY = "d70bdc756aae9a72b2f9024803584277";
        //String API_KEY = "Enter your API key";
        if(API_KEY == "Enter your API key"){
            Toast.makeText(getApplicationContext(), "API key not set", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8"); //encoding for cities with spaces, apostrophes
                DownloadTask task = new DownloadTask();
                task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=" + API_KEY);
                //api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.id_userText);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }
                return result.toString();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                ArrayList<String> message = new ArrayList<String>();
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                String temp_press_humid = jsonObject.getString("main");
                Log.i("main", temp_press_humid);
                JSONObject arrTPH = new JSONObject(temp_press_humid);
                String temp = arrTPH.getString("temp");
                String press = arrTPH.getString("pressure");
                String hum = arrTPH.getString("humidity");
                Log.i("temp",press);
                message.add("Temperature: "+temp+"K \n");
                message.add("Pressure: "+press+"mb \n");
                message.add("Humidity: "+hum+"% \n");

                Log.i("Weather content", weatherInfo);
                JSONArray arr = new JSONArray(weatherInfo);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String main = "";
                    String description = "";
                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");
                    if (main != "" && description != "") {
                        message.add(main + ": " + description + "\r\n");
                    }
                }
                if (message.size() != 0) {
                    //resultTextView.setText(message);
                    Intent intent = new Intent(MainActivity.this, com.example.weatherapp.Pop.class);
                    intent.putStringArrayListExtra("message", message);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

            }



        }
    }

}
