package com.bigdig.githubapiexample.di;

import com.bigdig.githubapiexample.ui.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, DataModule.class})
public interface AppComponent {

    void inject(MainPresenter mainPresenter);
}
