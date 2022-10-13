package com.example.api.services;

import com.example.api.services.dataResponse.InfoResponse;
import com.example.api.services.endpoints.InfoEndPoints;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoServices {
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<InfoResponse> getInfoService() {
        return this.getRetrofit().create(InfoEndPoints.class).getInfo();
    }
}
