package com.bigdig.githubapiexample.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView {
    void showProgress(boolean show);

    void showData(List<LocalRepoAndOwner> data);

    void showError(String err);
}
