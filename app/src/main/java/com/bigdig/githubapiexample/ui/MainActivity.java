package com.bigdig.githubapiexample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bigdig.githubapiexample.App;
import com.bigdig.githubapiexample.R;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;
import com.bigdig.githubapiexample.ui.adapter.LocalRepoRecyclerAdapter;
import com.bigdig.githubapiexample.util.UIUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends MvpAppCompatActivity
                            implements MainView, View.OnClickListener{


    @InjectPresenter
    MainPresenter presenter;

    private RecyclerView rvRepos;
    private TextInputEditText etGithubLogin;
    private View btGetRepos;

    private List<LocalRepoAndOwner> repoList = new ArrayList<>();

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

        presenter.onCreated();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_repos:
                String githubLogin = etGithubLogin.getText().toString();
                presenter.loadRepos(githubLogin);
                UIUtils.hideKeyboard(this, this);
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {
        if(show) progressDialog.show();
        else progressDialog.hide();
    }

    @Override
    public void showData(List<LocalRepoAndOwner> data) {
        repoList.clear();
        repoList.addAll(data);
        rvRepos.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
