package com.crrescita.employeetracker.activity.fragments.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crrescita.tel.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyRecordGridRecylerViewAdapter extends RecyclerView.Adapter<MyRecordGridRecylerViewAdapter.MyViewHolder> {

    private ArrayList<MyRecordModal> itemList;
    private Context context;

    public MyRecordGridRecylerViewAdapter(Context context, ArrayList<MyRecordModal> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_myrecord_view, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String title = itemList.get(position).getTitle();
        String icon = itemList.get(position).getIcon();
        String details = itemList.get(position).getDetails();

        holder.textViewTitle.setText(title);
        holder.textViewDescription.setText(details);

        Glide.with(context)
                .load(icon)
                .into(holder.imageViewIcon);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView imageViewIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        }
    }
}
