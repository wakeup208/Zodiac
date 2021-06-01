package com.wakeup.zodiac.model;

import com.wakeup.zodiac.presenter.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroclient {
    private static final String ROOT_URL = "http://horoscope-api.herokuapp.com/horoscope/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Api getapiservice() {
        return (Api) getRetrofitInstance().create(Api.class);
    }
}
