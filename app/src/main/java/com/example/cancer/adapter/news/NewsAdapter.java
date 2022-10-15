package com.example.cancer.adapter.news;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cancer.R;
import com.example.cancer.activities.MainActivity;
import com.example.cancer.fragments.NewsDetailFragment;
import com.example.cancer.models.news.Results;

import java.util.List;
import soup.neumorphism.NeumorphCardView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    List<Results> results;

    public NewsAdapter(Context context, List<Results> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        final Results r = results.get(position);

        holder.tvTitle.setText(r.getTitle());
        holder.tvDescription.setText(r.getDescription());
        holder.tvDate.setText(r.getPubDate());

        holder.cardView.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString("title", r.getTitle());
            b.putString("content", r.getContent());
            b.putString("date", r.getPubDate());
            b.putString("description", r.getDescription());
            NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
            newsDetailFragment.setArguments(b);
            MainActivity activity = (MainActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().add(R.id.MA, newsDetailFragment).commit();
//            Intent i = new Intent(context, NewsDetailActivity.class);
//            i.putExtra("title", r.getTitle());
//            i.putExtra("content", r.getContent());
//            i.putExtra("date", r.getPubDate());
//            i.putExtra("description", r.getDescription());
//            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvDate;
        NeumorphCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDate = itemView.findViewById(R.id.tv_date);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
