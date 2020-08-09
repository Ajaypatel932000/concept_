package com.example.concept_git.notification_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.concept_git.R;
import com.example.concept_git.show_test.ListItems_test;
import com.example.concept_git.show_test.MyAdapter_test_list;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<NotiListItems> notiListItems;
    private Context context;

    public NotificationAdapter(List<NotiListItems> notiListItems, Context context) {
        this.notiListItems = notiListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_card,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        final NotiListItems items=notiListItems.get(position);
        holder.textViewDate.setText(items.getDate());
        holder.textViewMsg.setText(items.getNotification());
    }

    @Override
    public int getItemCount() {
        return notiListItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewDate, textViewMsg;
        RelativeLayout relativeLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.tv1);
            textViewMsg = itemView.findViewById(R.id.tv2);
            relativeLayout = itemView.findViewById(R.id.relative_layout_test);

        }
    }}
