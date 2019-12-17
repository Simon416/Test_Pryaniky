package com.example.test_pryaniky.data;

import com.example.test_pryaniky.domain.model.Data;
import com.google.gson.GsonBuilder;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataServerImpl implements DataServer {
    private ApiServer apiServer;

    public DataServerImpl() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Data.class, new DataJsonDeserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("https://chat.pryaniky.com/")
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiServer = retrofit.create(ApiServer.class);
    }

    @Override
    public Single<Data> getDataFromServer() {
        return apiServer.getMainData();
    }
}
