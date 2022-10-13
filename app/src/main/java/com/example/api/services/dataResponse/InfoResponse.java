package com.example.api.services.dataResponse;

import com.example.api.services.models.InfoApi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoResponse {
    @SerializedName("date") public String date;
    @SerializedName("data") public List<InfoApi> data;
}
