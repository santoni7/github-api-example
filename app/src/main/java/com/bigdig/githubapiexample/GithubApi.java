package com.bigdig.githubapiexample;

import com.bigdig.githubapiexample.model.Repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
    @GET("users/{user}/repos")
    Observable<List<Repo>> getUserRepos(@Path("user") String userName);
}
