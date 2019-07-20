package com.bigdig.githubapiexample.datasource;

import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;
import com.bigdig.githubapiexample.model.mapper.RepoToLocalRepoMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Класс, который отвечает за получение, обработку, и сохранение репозиториев
 * Программа должна обращаться к нему, когда надо получить репозитории, а не отдельно к апи или базе
 */
public class RepoDataSource {
    private LocalRepoDataSource localRepoDataSource;
    private RemoteRepoDataSource remoteRepoDataSource;

    @Inject
    public RepoDataSource(LocalRepoDataSource localRepoDataSource, RemoteRepoDataSource remoteRepoDataSource) {
        this.localRepoDataSource = localRepoDataSource;
        this.remoteRepoDataSource = remoteRepoDataSource;
    }

    @SuppressWarnings("Convert2MethodRef")
    public Flowable<List<LocalRepoAndOwner>> getReposByLogin(final String login){
        return remoteRepoDataSource.getRepos(login)
                // Преобразуем список, который мы получили с апи в список, пригодный для базы данных:
                .map(repoList -> {
                    return RepoToLocalRepoMapper.mapList(repoList);
                })
                // flatMap, потому что внутри у нас тоже Observable'ы (или Flowable'ы - не важно)
                .flatMap(localRepoAndOwners -> {
                    // Записать в базу все репозитории и владельца
                    return localRepoDataSource.insertAll(localRepoAndOwners)
                            // А потом считать их с базы данных
                            .andThen(localRepoDataSource.getReposByLogin(login));
                })
                // На случай ошибки (например, нет интернета) - возвращаем то, что есть в бд
                .onErrorResumeNext(err -> {
                    return localRepoDataSource.getReposByLogin(login);
                });
    }

    public Flowable<List<LocalRepoAndOwner>> getAllLocalRepos(){
        return localRepoDataSource.getAllRepos();
    }
}
