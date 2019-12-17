package com.example.test_pryaniky.data;

import com.example.test_pryaniky.domain.model.Data;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiServer {
    @GET("json/JSONSample.json")
    Single<Data> getMainData();
}
