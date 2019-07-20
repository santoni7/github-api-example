package com.bigdig.githubapiexample.di;

import android.content.Context;

import androidx.room.Room;

import com.bigdig.githubapiexample.database.AppDatabase;
import com.bigdig.githubapiexample.database.LocalRepoDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class})
public class DataModule {
    private Context context;

    public DataModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public AppDatabase provideDatabase(){
        return Room.databaseBuilder(context, AppDatabase.class, "my_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public LocalRepoDAO provideRepoDAO(AppDatabase appDatabase){
        return appDatabase.localRepoDAO();
    }


// Эти зависимости инжектятся через конструктор!!! :

//    @Provides
//    @Singleton
//    public LocalRepoDataSource localRepoDataSource(LocalRepoDAO dao){
//        return new LocalRepoDataSource(dao);
//    }
//
//    @Provides
//    @Singleton
//    public RemoteRepoDataSource remoteRepoDataSource(GithubApi githubApi){
//        return new RemoteRepoDataSource(githubApi);
//    }

//    @Provides
//    @Singleton
//    public RepoDataSource repoDataRepository(LocalRepoDataSource localRepoDataSource,
//                                             RemoteRepoDataSource remoteRepoDataSource){
//        return new RepoDataSource(localRepoDataSource, remoteRepoDataSource);
//    }

}
