package com.example.android.sunshine.sync;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

//  COMPLETED (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {
    //  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    synchronized public static void syncWeather(Context context) {
//      COMPLETED (3) Within syncWeather, fetch new weather data
        URL url = NetworkUtils.getUrl(context);
        try {
            String weatherDataJson = NetworkUtils.getResponseFromHttpUrl(url);
//      COMPLETED (4) If we have valid results, delete the old data and insert the new
            if (!TextUtils.isEmpty(weatherDataJson)) {
                ContentValues[] weatherData = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, weatherDataJson);
                if (weatherData.length > 0) {
                    context
                            .getContentResolver()
                            .bulkInsert(
                                    WeatherContract.WeatherEntry.CONTENT_URI,
                                    weatherData);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}