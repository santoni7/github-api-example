package com.bigdig.githubapiexample.model.local;

import androidx.room.Embedded;

public class LocalRepoAndOwner {
    @Embedded
    LocalRepo repo;
    @Embedded
    LocalOwner owner;

    public LocalRepoAndOwner(){

    }

    public LocalRepoAndOwner(LocalRepo repo, LocalOwner owner) {
        this.repo = repo;
        this.owner = owner;
    }

    public LocalRepo getRepo() {
        return repo;
    }

    public void setRepo(LocalRepo repo) {
        this.repo = repo;
    }

    public LocalOwner getOwner() {
        return owner;
    }

    public void setOwner(LocalOwner owner) {
        this.owner = owner;
    }
}
