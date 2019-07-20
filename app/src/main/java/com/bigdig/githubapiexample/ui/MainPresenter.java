package com.bigdig.githubapiexample.ui;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bigdig.githubapiexample.App;
import com.bigdig.githubapiexample.datasource.RemoteRepoDataSource;
import com.bigdig.githubapiexample.datasource.RepoDataSource;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    RepoDataSource repoDataSource;




    public void onCreated(){
        App.getInstance().getAppComponent().inject(this);

        loadRepos(null);
    }

    /**
     * Load repos of githubLogin user, or get all local repositories if githubLogin null or empty
     */
    public void loadRepos(@Nullable String githubLogin){
        // Очищаем наши предыдущие запросы, чтобы они не мешали друг другу
        compositeDisposable.clear();
        // Если пользователь ввел что-то, то отображаем только репозитории выбраного пользователя
        // Если же пользователь оставил пустую строку - получаем с БД все репозитории что есть
        Flowable<List<LocalRepoAndOwner>> reposFlowable;
        if(!TextUtils.isEmpty(githubLogin)) {
            reposFlowable = repoDataSource.getReposByLogin(githubLogin);
        } else {
            reposFlowable = repoDataSource.getAllLocalRepos();
        }

        // Показать загрузчик
        getViewState().showProgress(true);

        compositeDisposable.add(reposFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repoListResponse -> {
                    // Убрать загрузчик
                    getViewState().showProgress(false);
                    getViewState().showData(repoListResponse);
                }, error -> {
                    // Убрать загрузчик
                    getViewState().showProgress(false);
                    // Отобразить пустой список
                    getViewState().showData(new ArrayList<>());
                    getViewState().showError(error.getMessage());
                }));
    }


    public void onDestroy(){
        compositeDisposable.clear();
    }
}
