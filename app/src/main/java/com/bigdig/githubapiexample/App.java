package com.bigdig.githubapiexample;

import android.app.Application;

import androidx.room.Room;

import com.bigdig.githubapiexample.api.GithubService;
import com.bigdig.githubapiexample.database.AppDatabase;
import com.bigdig.githubapiexample.datasource.LocalRepoDataSource;
import com.bigdig.githubapiexample.datasource.RemoteRepoDataSource;
import com.bigdig.githubapiexample.datasource.RepoDataRepository;

public class App extends Application {

    // Для получения доступа к классу из любого места приложения:
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private AppDatabase appDatabase;
    private RepoDataRepository repoDataRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "my_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public RepoDataRepository getRepoDataRepository() {
        return new RepoDataRepository(
                new LocalRepoDataSource(appDatabase.localRepoDAO()),
                new RemoteRepoDataSource(GithubService.getApi())
        );
    }
}
