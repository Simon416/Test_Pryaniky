package com.example.test_pryaniky.data;

import com.example.test_pryaniky.domain.model.Data;

import io.reactivex.Single;

public interface DataServer {
    Single<Data> getDataFromServer();
}
