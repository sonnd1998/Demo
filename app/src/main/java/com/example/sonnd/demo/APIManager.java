package com.example.sonnd.demo;

import java.util.HashMap;

public class APIManager {
    private String urlStr, requestMethod;
    private HashMap<String, String> params, headers;
    private OnLoadedCallback callback;
    private APIManager(APIManagerBuilder builder){
        this.urlStr = builder.nestedUrlStr;
        this.requestMethod = builder.nestedRequestMethod;
        this.params = builder.nestedParams;
        this.headers = builder.nestedHeaders;

    }

    public class APIManagerBuilder{
        private String nestedUrlStr, nestedRequestMethod;
        private HashMap<String, String> nestedParams, nestedHeaders;
        private OnLoadedCallback nestedCallback;
        public APIManagerBuilder(){

        }

        public APIManagerBuilder setUrlStr(String urlStr){
            this.nestedUrlStr = urlStr;
            return this;
        }

        public APIManagerBuilder setResquestMethod(String requestMethod){
            this.nestedRequestMethod = requestMethod;
            return this;
        }

        public APIManagerBuilder setParams(HashMap<String, String> params){
            this.nestedParams = params;
            return this;
        }

        public APIManagerBuilder setHeaders(HashMap<String,String> headers){
            this.nestedHeaders = headers;
            return this;
        }

        public APIManagerBuilder setCallback(OnLoadedCallback callback){
            this.nestedCallback = callback;
            return this;
        }
        public APIManager request(){

            return new APIManager(this);
        }
    }
}
