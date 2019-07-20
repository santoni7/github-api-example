package com.bigdig.githubapiexample;

import android.app.Application;

import com.bigdig.githubapiexample.di.AppComponent;
import com.bigdig.githubapiexample.di.DaggerAppComponent;
import com.bigdig.githubapiexample.di.DataModule;
import com.bigdig.githubapiexample.di.NetworkModule;

public class App extends Application {

    // Для получения доступа к классу из любого места приложения:
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .dataModule(new DataModule(getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
