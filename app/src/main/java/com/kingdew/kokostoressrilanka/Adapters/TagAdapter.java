package com.kingdew.kokostoressrilanka.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kingdew.kokostoressrilanka.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    Context context;
    ArrayList<String> tags;

    public TagAdapter(Context context, ArrayList<String> tags) {
        this.context = context;
        this.tags = tags;
    }

    @NonNull
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, int position) {
        holder.tagid.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagid=itemView.findViewById(R.id.tagid);
        }
    }
}
