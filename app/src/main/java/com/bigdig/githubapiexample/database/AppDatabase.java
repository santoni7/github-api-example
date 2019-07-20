package com.bigdig.githubapiexample.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bigdig.githubapiexample.model.local.LocalOwner;
import com.bigdig.githubapiexample.model.local.LocalRepo;

@Database(
        entities = {LocalRepo.class, LocalOwner.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocalRepoDAO localRepoDAO();
}
