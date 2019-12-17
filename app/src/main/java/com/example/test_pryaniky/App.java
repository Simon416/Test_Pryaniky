package com.example.test_pryaniky;

import android.app.Application;

import com.example.test_pryaniky.data.DataServerImpl;
import com.example.test_pryaniky.data.DataServer;

public class App extends Application {

    private DataServer dataServer;
    @Override
    public void onCreate() {
        super.onCreate();
        dataServer = new DataServerImpl();
    }

    public DataServer getIDataServer() { return dataServer;}
}
