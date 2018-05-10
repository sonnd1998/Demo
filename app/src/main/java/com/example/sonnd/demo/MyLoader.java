package com.example.sonnd.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyLoader extends BaseLoader<WeatherEntity> {

    public MyLoader(@NonNull Context context, Bundle properties ) {
        super(context, properties,WeatherEntity.class);
    }

    @Nullable
    @Override
    public WeatherEntity loadInBackground() {
        return super.loadInBackground();
    }
}
