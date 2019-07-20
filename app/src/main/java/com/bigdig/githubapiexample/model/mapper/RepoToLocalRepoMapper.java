package com.bigdig.githubapiexample.model.mapper;

import com.bigdig.githubapiexample.model.Owner;
import com.bigdig.githubapiexample.model.Repo;
import com.bigdig.githubapiexample.model.local.LocalOwner;
import com.bigdig.githubapiexample.model.local.LocalRepo;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс с несколькими статическими методами, предназначенный для конвертации
 * модели, которую мы получаем с апи в модель, которую мы записываем в базу
 */
public class RepoToLocalRepoMapper {
    public static LocalRepo mapRepo(Repo repo){
        LocalRepo res = new LocalRepo();

        res.setRepoId(repo.getId());
        res.setOwnerLogin(repo.getOwner().getLogin());
        res.setName(repo.getName());
        res.setFullName(repo.getFullName());
        res.setGitUrl(repo.getGitUrl());
        res.setCreatedAt(repo.getCreatedAt());
        res.setUpdatedAt(repo.getUpdatedAt());
        return res;
    }

    public static LocalOwner mapOwner(Owner owner){
        LocalOwner res = new LocalOwner();
        res.setLogin(owner.getLogin());
        res.setId(owner.getId());
        res.setAvatarUrl(owner.getAvatarUrl());
        res.setUrl(owner.getUrl());
        return res;
    }

    public static List<LocalRepoAndOwner> mapList(List<Repo> repos){
        List<LocalRepoAndOwner> list = new ArrayList<>();
        for(Repo repo: repos){
            LocalRepo localRepo = mapRepo(repo);
            LocalOwner localOwner = mapOwner(repo.getOwner());
            list.add(new LocalRepoAndOwner(localRepo, localOwner));
        }
        return list;
    }
}
