package com.bigdig.githubapiexample.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bigdig.githubapiexample.model.local.LocalOwner;
import com.bigdig.githubapiexample.model.local.LocalRepo;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LocalRepoDAO {

    // OnConflictStrategy.REPLACE значит, что если мы попробуем вставить пользователя или репозиторий,
    // которые уже есть в базе, то они перезапишутся
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepo(LocalRepo repo);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOwner(LocalOwner owner);

    @Update
    void updateRepo(LocalRepo repo);
    @Update
    void updateOwner(LocalOwner owner);

    // Получаем с базы все репозитории и обьединяем с табличной пользователей по полю логин
    @Query("SELECT LocalRepo.*, LocalOwner.* FROM LocalRepo JOIN LocalOwner ON LocalOwner.login=LocalRepo.ownerLogin")
    Flowable<List<LocalRepoAndOwner>> selectAllRepos();

    // Получаем с базы все репозитории одного пользователя и обьединяем с табличкой пользователей по полю логин (в нижнем регистре, сделано через LOWER)
    @Query("SELECT LocalRepo.*, LocalOwner.* FROM LocalRepo JOIN LocalOwner ON LocalOwner.login=LocalRepo.ownerLogin WHERE :userLogin LIKE LocalRepo.ownerLogin")
    Flowable<List<LocalRepoAndOwner>> selectReposByLogin(String userLogin);


    @Query("SELECT * FROM LocalRepo")
    Flowable<List<LocalRepo>> selectOnlyRepos();

    @Query("SELECT * FROM LocalOwner")
    Flowable<List<LocalOwner>> selectOnlyOwners();

    @Delete
    void delete(LocalRepo repo);
    @Delete
    void delete(LocalOwner owner);

}
