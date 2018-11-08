package com.example.khalitko.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";
    ForecastQuery query;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        progressBar = findViewById(R.id.weatherPBar);
        progressBar.setVisibility(View.VISIBLE);

        query = new ForecastQuery();
        query.execute();

    }


    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String current, min, max, wind, icon, city;
        Bitmap picture;
        String currentT = getResources().getString(R.string.currentTemp);
        String lowT = getResources().getString(R.string.lowTemp);
        String maxT = getResources().getString(R.string.maxTemp);
        String windS = getResources().getString(R.string.windSpeed);

        @Override
        public String doInBackground(String... args) {
            try {
                //connect to Server:
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Read the XML:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");


                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    switch(xpp.getEventType()) {

                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();

                            if (name.equals("temperature")) {
                                current = xpp.getAttributeValue(null, "value");
                                publishProgress(20);
                                min = xpp.getAttributeValue(null, "min");
                                publishProgress(40);
                                max = xpp.getAttributeValue(null, "max");
                                publishProgress(60);
                                Log.i(ACTIVITY_NAME, "value " + current + " min " + min + " max " + max);
                            } else if (name.equals("speed")) {
                                wind = xpp.getAttributeValue(null, "value");
                                publishProgress(80);
                            } else if (name.equals("weather")) {
                                icon = xpp.getAttributeValue(null, "icon");
                            } else if (name.equals("city")) {
                                city = xpp.getAttributeValue(null, "name");
                            }
                            Log.i("read XML tag:", name);
                            break;

                        case XmlPullParser.TEXT:
                            break;
                    }
                    xpp.next();
                }
            } catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }
            if (!fileExistance(icon + ".png")) {
                try {
                    picture = HttpUtils.getImage("http://openweathermap.org/img/w/" + icon + ".png");
                    publishProgress(100);
                    FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(ACTIVITY_NAME, "Downloading image " + icon + ".png");
                } catch (Exception e) {
                    Log.i("Exception", e.getMessage());
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = openFileInput(icon + ".png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                picture = BitmapFactory.decodeStream(fis);
                Log.i(ACTIVITY_NAME, "Getting image from file " + icon + ".png");

            }
            return "done";
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        public void onProgressUpdate(Integer... args) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(args[0]);
        }

        @Override
        public void onPostExecute(String result) {


            TextView currentV = findViewById(R.id.current);
            TextView minV = findViewById(R.id.min);
            TextView maxV = findViewById(R.id.max);
            TextView windV = findViewById(R.id.wind);
            ImageView imageV = findViewById(R.id.image);
            TextView cityN = findViewById(R.id.city);
            cityN.setText(city);
            currentV.setText(currentT + ": " + current + " °C");
            maxV.setText(lowT + ": " + min + " °C");
            minV.setText(maxT + ": " + max + " °C");
            windV.setText(windS + ": " + wind + " km/h");
            imageV.setImageBitmap(picture);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    static class HttpUtils {
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public static Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }
}