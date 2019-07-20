package com.bigdig.githubapiexample.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigdig.githubapiexample.App;
import com.bigdig.githubapiexample.R;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;
import com.bigdig.githubapiexample.util.UIUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener{

    private static final String TAG = "mLog";

    private RecyclerView rvRepos;
    private TextInputEditText etGithubLogin;
    private View btGetRepos;

    private List<LocalRepoAndOwner> repoList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRepos = findViewById(R.id.rv_repos);
        etGithubLogin = findViewById(R.id.et_github_login);
        btGetRepos = findViewById(R.id.btn_get_repos);

        btGetRepos.setOnClickListener(this);

        LocalRepoRecyclerAdapter adapter = new LocalRepoRecyclerAdapter(repoList);
        rvRepos.setAdapter(adapter);
        rvRepos.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        );
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvRepos.addItemDecoration(itemDecor);

        progressDialog = new MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setView(R.layout.dialog_progress)
                .show();

        updateRepos(""); // Get all repos
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_repos:
                String githubLogin = etGithubLogin.getText().toString();
                updateRepos(githubLogin);
                UIUtils.hideKeyboard(this, this);
                break;
        }
    }

    public void updateRepos(String githubLogin){
        // Очищаем наши предыдущие запросы, чтобы они не мешали друг другу
        compositeDisposable.clear();
        // Если пользователь ввел что-то, то отображаем только репозитории выбраного пользователя
        // Если же пользователь оставил пустую строку - получаем с БД все репозитории что есть
        Flowable<List<LocalRepoAndOwner>> reposFlowable;
        if(!TextUtils.isEmpty(githubLogin)) {
            reposFlowable = App.getInstance().getRepoDataRepository()
                    .getReposByLogin(githubLogin);
        } else {
            reposFlowable = App.getInstance().getRepoDataRepository()
                    .getAllLocalRepos();
        }

        progressDialog.show();
        Disposable d = reposFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repoListResponse -> {
                    progressDialog.hide();

                    repoList.clear();
                    repoList.addAll(repoListResponse);
                    rvRepos.getAdapter().notifyDataSetChanged();
                }, error -> {
                    progressDialog.hide();

                    repoList.clear();
                    rvRepos.getAdapter().notifyDataSetChanged();
                    Log.e("mLog", "Error: " + error.getMessage());
                    Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        compositeDisposable.add(d);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
