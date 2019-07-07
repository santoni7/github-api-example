package com.bigdig.githubapiexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigdig.githubapiexample.model.Repo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener{

    private static final String TAG = "mLog";

    private RecyclerView rvRepos;
    private EditText etGithubLogin;
    private Button btGetRepos;

    private List<Repo> repoList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRepos = findViewById(R.id.rv_repos);
        etGithubLogin = findViewById(R.id.et_github_login);
        btGetRepos = findViewById(R.id.btn_get_repos);

        btGetRepos.setOnClickListener(this);

        RepositoryAdapter adapter = new RepositoryAdapter(repoList);
        rvRepos.setAdapter(adapter);
        rvRepos.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false)
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_repos:
                String githubLogin = etGithubLogin.getText().toString();
                updateRepos(githubLogin);
                break;
        }
    }

    public void updateRepos(String githubLogin){
        Disposable d = GithubService.getApi()
                .getUserRepos(githubLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repoListResponse -> {
                    repoList.clear();
                    repoList.addAll(repoListResponse);
                    rvRepos.getAdapter().notifyDataSetChanged();
                }, error -> {
                    Log.e("mLog", "Error: " + error.getMessage());
                });
        compositeDisposable.add(d);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
