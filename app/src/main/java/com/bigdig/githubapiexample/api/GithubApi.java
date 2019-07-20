package com.bigdig.githubapiexample.api;

import com.bigdig.githubapiexample.model.Repo;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
    @GET("users/{user}/repos")
    Flowable<List<Repo>> getUserRepos(@Path("user") String userName);
}
