package com.bigdig.githubapiexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigdig.githubapiexample.model.Repo;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepoViewHolder> {

    private List<Repo> repoList;

    public RepositoryAdapter(List<Repo> repoList) {
        this.repoList = repoList;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_repo, viewGroup, false);
        return new RepoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder repoViewHolder, int i) {
        Repo repo = repoList.get(i);
        repoViewHolder.bind(repo);
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRepoName;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRepoName = itemView.findViewById(R.id.tv_repo_name);
        }

        public void bind(Repo repo) {
            tvRepoName.setText(repo.getName());
        }
    }
}
