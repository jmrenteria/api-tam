package com.example.api.services.endpoints;

import com.example.api.services.dataResponse.InfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InfoEndPoints {
    //URL Endpoint
    @GET("api/users.json")
    Call<InfoResponse> getInfo();
}
