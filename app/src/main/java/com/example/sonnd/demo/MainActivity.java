package com.example.sonnd.demo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnLoadedCallback {
    private ProgressDialog dialog;
    private Bundle b = new Bundle();
    private String API = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        b.putString("url", API);
        b.putString("requestMethod", "GET");
        b.putSerializable("headers", null);
        b.putSerializable("params", null);
        getSupportLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<WeatherEntity>() {

            @NonNull
            @Override
            public Loader<WeatherEntity> onCreateLoader(int id, @Nullable Bundle args) {
                return new MyLoader(MainActivity.this, b);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<WeatherEntity> loader, WeatherEntity data) {
                Log.e("loader manager", data.getMain().getTemp_max() + "");
            }

            @Override
            public void onLoaderReset(@NonNull Loader<WeatherEntity> loader) {

            }
        });
    }

    @Override
    public void onLoaded() {

    }
}