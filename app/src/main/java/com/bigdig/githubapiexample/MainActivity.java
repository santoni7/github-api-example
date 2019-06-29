package com.bigdig.githubapiexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bigdig.githubapiexample.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GithubService.getApi().getUserRepos("santoni7")
                .enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        if(response.code() == 200){
                            List<Repo> repos = response.body();
                            for(Repo r : repos){
                                Log.d(TAG, "Repo: name=" + r.getName() + "; ownerLogin=" +
                                        r.getOwner().getLogin());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }
}
