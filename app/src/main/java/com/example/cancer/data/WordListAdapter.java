package com.example.cancer.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cancer.R;

import java.util.ArrayList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    public interface OnWordClickListener{
        void onWordClick(Word word, int position);
    }

    private ArrayList<Word> data;
    private final Context context;
    private final OnWordClickListener onClickListener;

    public WordListAdapter(Context context, ArrayList<Word> data, OnWordClickListener onClickListener){
        this.context = context;
        this.data = data;
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(data.get(position).name);
        Word word = data.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onWordClick(word, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
