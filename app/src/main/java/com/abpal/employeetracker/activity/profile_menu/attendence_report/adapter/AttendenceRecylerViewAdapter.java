package com.abpal.employeetracker.activity.profile_menu.attendence_report.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abpal.tel.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AttendenceRecylerViewAdapter extends RecyclerView.Adapter<AttendenceRecylerViewAdapter.MyViewHolder> {

    private ArrayList<AttendenceModal> itemList;
    private Context context;

    public AttendenceRecylerViewAdapter(Context context,ArrayList<AttendenceModal> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String date = itemList.get(position).getDate();
        String attendence = itemList.get(position).getAttendence();
        String checkIn = itemList.get(position).getCheckIn();
        String checkOut = itemList.get(position).getCheckout();
        String totalHours = itemList.get(position).getTotalHours();

        holder.checkoutEditText.setText(checkOut);
        holder.checkInEditText.setText(checkIn);
        holder.totalTimeEditText.setText(totalHours);
        holder.textViewDateAndDay.setText(date);
        holder.textViewAttendence.setText(attendence);
        
        if(attendence.equalsIgnoreCase("Present")){
            holder.textViewAttendence.setTextColor(context.getResources().getColor(R.color.attendence_green));
        } else if (attendence.equalsIgnoreCase("Absent")) {
            holder.textViewAttendence.setTextColor(context.getResources().getColor(R.color.attendence_red));
        }else{
            holder.textViewAttendence.setTextColor(context.getResources().getColor(R.color.attendence_orange));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;
        TextInputEditText totalTimeEditText;
        TextInputEditText checkoutEditText;
        TextInputEditText checkInEditText;
        TextView textViewDateAndDay;
        TextView textViewAttendence;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            totalTimeEditText = itemView.findViewById(R.id.totalTimeEditText);
            checkoutEditText = itemView.findViewById(R.id.checkoutEditText);
            checkInEditText = itemView.findViewById(R.id.checkInEditText);
            textViewDateAndDay = itemView.findViewById(R.id.textViewDateAndDay);
            textViewAttendence = itemView.findViewById(R.id.textViewAttendence);
        }
    }
}
