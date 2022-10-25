package com.example.api.services.endpoints;

import com.example.api.services.dataResponse.InfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface InfoEndPoints {
    @Headers("code-app:2022*01")
    @GET("api/users")
    Call<InfoResponse> getInfo();
}
