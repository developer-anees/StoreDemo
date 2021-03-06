package com.digitalbuddha.daodemo.reddit.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalbuddha.daodemo.reddit.data.model.Children;
import com.nytimes.storedemo.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by brianplummer on 12/16/15.
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private List<Children> articles = new ArrayList<>();

    @Inject
    public PostAdapter() {}

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.onBind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Children> articlesToAdd) {
        articles.clear();
        articles.addAll(articlesToAdd);
        notifyDataSetChanged();
    }

    public void addArticle(Children article) {
        articles.add(article);
        notifyDataSetChanged();
    }
}
