package com.example.sonnd.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {
    private Bundle properties;
    private Class<T> tClass;
    private T mData;
    private OnLoadedCallback callback;
    private boolean hasData = false;
    private String urlStr, requestMethod;
    private HashMap<String,String> params, headers;
    public BaseLoader(@NonNull Context context, Bundle properties, Class<T> mClass) {
        super(context);
        this.properties = properties;
        urlStr =  properties.getString("url");
        requestMethod = properties.getString("requestMethod");
        params = (HashMap<String, String>) properties.getSerializable("params");
        headers = (HashMap<String, String>) properties.getSerializable("headers");
        tClass = mClass;
    }

    @Nullable
    @Override
    public T loadInBackground() {
        Log.e("loader","loadInBG");
        if(urlStr!=null && !urlStr.equals("")){
            try {
                String sub="";
                if(params!=null){
                    if(urlStr.contains("?"));
                    else sub+="?";
                }
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                if(headers!=null) for(Map.Entry en: headers.entrySet()){
                    conn.setRequestProperty(en.getKey().toString(), en.getValue().toString());
                }
                int i = 0;
                if(params!=null) for(Map.Entry en: params.entrySet()){
                    sub = sub+en.getKey().toString()+"="+en.getValue().toString();
                    if(i!= params.entrySet().size()-1) {
                        sub = sub+"&";
                        i++;
                    }
                }
                conn.setRequestMethod(requestMethod);
                if(requestMethod.equals("POST")){
                    conn.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                    writer.write(sub);
                    writer.close();
                }
                conn.connect();
                Log.e("url",urlStr);
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                StringBuffer sb = new StringBuffer();
                String line ;
                while((line = br.readLine())!=null){
                    sb.append(line+"\n");
                }
                Log.e("sb String",sb.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                return gson.fromJson(sb.toString(),tClass);
            } catch (Exception e) {
                Log.e("exception",e.toString());
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public T onLoadInBackground() {
        Log.e("loader","onLoadInBG");
        return super.onLoadInBackground();
    }

    @Override
    protected void onStartLoading() {
        Log.e("loader","onStartLoading");
        super.onStartLoading();
        if(takeContentChanged() || !hasData){
            forceLoad();
        }
        else if(hasData) deliverResult(mData);
    }

    @Override
    protected void onReset() {
        Log.e("loader","onReset");
        super.onReset();
        onStopLoading();
        if(hasData){
            mData = null;
            hasData = false;
        }
    }

    @Override
    public void deliverResult(@Nullable T data) {
        Log.e("loader","deliverResult");
        hasData = true;
        mData = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStopLoading() {
        Log.e("loader","onStopLoading");
        cancelLoad();
        super.onStopLoading();
    }

    @Override
    public void onContentChanged() {
        Log.e("loader","onStartLoading");
        super.onContentChanged();
    }

    @Override
    protected boolean onCancelLoad() {
        Log.e("loader","onCancalLoad");
        return super.onCancelLoad();
    }

    @Override
    protected void onAbandon() {
        Log.e("loader","onAbandon");
        super.onAbandon();
    }

    @Override
    protected void onForceLoad() {
        Log.e("loader","onForceLoad");
        super.onForceLoad();
    }

    @Override
    public void onCanceled(@Nullable T data) {
        Log.e("loader","onCanceled");
        super.onCanceled(data);
    }

}
