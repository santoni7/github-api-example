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
        GithubService.getApi().getUserRepos(githubLogin)
                .enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Repo>> call, @NotNull Response<List<Repo>> response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        if(response.code() == 200){
                            List<Repo> repos = response.body();
                            repoList.clear();
                            repoList.addAll(repos);
                            rvRepos.getAdapter().notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Repo>> call, @NotNull Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }
}
