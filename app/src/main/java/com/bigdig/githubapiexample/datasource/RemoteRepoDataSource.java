package com.bigdig.githubapiexample.datasource;

import com.bigdig.githubapiexample.api.GithubApi;
import com.bigdig.githubapiexample.model.Repo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class RemoteRepoDataSource {

    private GithubApi githubApi;

    @Inject
    public RemoteRepoDataSource(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public Flowable<List<Repo>> getRepos(String login){
        return githubApi.getUserRepos(login);
    }
}
