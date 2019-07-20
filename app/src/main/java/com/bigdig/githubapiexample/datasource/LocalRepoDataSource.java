package com.bigdig.githubapiexample.datasource;

import com.bigdig.githubapiexample.database.LocalRepoDAO;
import com.bigdig.githubapiexample.model.local.LocalRepo;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Класс, который отвечает за работу с базой данных. Все действия без результата здесь оборачуются в
 * Completable, чтобы мы могли потом указать в каком потоке их делать, и так просто удобнее использовать
 * эти действия при написании реактивного кода
 */
public class LocalRepoDataSource {
    private LocalRepoDAO dao;

    public LocalRepoDataSource(LocalRepoDAO dao){
        this.dao = dao;
    }

    public Completable insert(LocalRepo repo){
        return Completable.fromAction(() -> {
            dao.insertRepo(repo);
        });
    }

    public Completable insertAll(List<LocalRepoAndOwner> localRepoAndOwnerList){
        return Completable.fromAction(() -> {
            for(LocalRepoAndOwner repoAndOwner: localRepoAndOwnerList){

                // Важно!! Сначала добавляем хазяина, потом - репозиторий.
                // Иначе будет ошибка при добавлении репозитория
                dao.insertOwner(repoAndOwner.getOwner());
                dao.insertRepo(repoAndOwner.getRepo());
            }
        });
    }

    public Completable update(LocalRepo repo){
        return Completable.fromAction(() -> {
            dao.updateRepo(repo);
        });
    }

    public Flowable<List<LocalRepoAndOwner>> getAllRepos(){
        return dao.selectAllRepos();
    }

    public Flowable<List<LocalRepoAndOwner>> getReposByLogin(String login){
        return dao.selectReposByLogin(login);
    }
}
