package com.bigdig.githubapiexample.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdig.githubapiexample.R;
import com.bigdig.githubapiexample.model.Repo;
import com.bigdig.githubapiexample.model.local.LocalRepo;
import com.bigdig.githubapiexample.model.local.LocalRepoAndOwner;
import com.bumptech.glide.Glide;

import java.util.List;

public class LocalRepoRecyclerAdapter extends RecyclerView.Adapter<LocalRepoRecyclerAdapter.RepoViewHolder> {

    private List<LocalRepoAndOwner> repoList;

    public LocalRepoRecyclerAdapter(List<LocalRepoAndOwner> repoList) {
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
        LocalRepoAndOwner repo = repoList.get(i);
        repoViewHolder.bind(repo, i);
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRepoName;
        private TextView tvOwnerName;
        private TextView tvItemPos;
        private ImageView ivOwnerAva;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRepoName = itemView.findViewById(R.id.tv_repo_name);
            tvOwnerName = itemView.findViewById(R.id.tv_owner_name);
            ivOwnerAva = itemView.findViewById(R.id.iv_owner_avatar);
            tvItemPos = itemView.findViewById(R.id.tv_item_pos);
        }

        public void bind(LocalRepoAndOwner repoAndOwner, int position) {
            tvItemPos.setText(String.valueOf(position + 1));
            tvRepoName.setText(repoAndOwner.getRepo().getName());
            tvOwnerName.setText(repoAndOwner.getOwner().getLogin());
            Glide.with(itemView)
                    .load(repoAndOwner.getOwner().getAvatarUrl())
                    .into(ivOwnerAva);
        }
    }
}
