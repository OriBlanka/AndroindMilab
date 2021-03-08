package com.runbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupAdp extends RecyclerView.Adapter<GroupAdp.ViewHolder> {

    private Fragment activity;
    ArrayList<String> arrayListGroup;

    public GroupAdp(Fragment activity, ArrayList<String> arrayListGroup) {
        this.activity = activity;
        this.arrayListGroup = arrayListGroup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_group, parent, false);
        return new GroupAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(arrayListGroup.get(position));

        ArrayList<String> arrayListActivity = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            arrayListActivity.add("Member " + i);
        }
        ActivityAdp adapterActivity = new ActivityAdp(arrayListActivity);
        LinearLayoutManager LayoutManagerActivity = new LinearLayoutManager(activity.getContext());
        holder.rvActivity.setLayoutManager(LayoutManagerActivity);
        holder.rvActivity.setAdapter(adapterActivity);
    }

    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        RecyclerView rvActivity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            rvActivity = itemView.findViewById(R.id.rvActivity);
        }
    }
}
