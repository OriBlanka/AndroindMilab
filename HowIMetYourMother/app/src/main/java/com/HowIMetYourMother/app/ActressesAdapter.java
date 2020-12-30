package com.HowIMetYourMother.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActressesAdapter extends RecyclerView.Adapter<ActressesAdapter.MyViewHolder> {
    String actressNames[];
    String actressRealNames[];
    int actressImage[];
    Context context;

    public ActressesAdapter(Context ct, String names[], String realNames[], int images[]){
        context = ct;
        actressNames = names;
        actressRealNames = realNames;
        actressImage = images;
    }

    @NonNull
    @Override
    public ActressesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new ActressesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActressesAdapter.MyViewHolder holder, int position) {
        holder.nameText.setText(actressNames[position]);
        holder.realNameText.setText(actressRealNames[position]);
        holder.actressImage.setImageResource(actressImage[position]);
    }

    @Override
    public int getItemCount() {
        return actressImage.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, realNameText;
        ImageView actressImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.actorName);
            realNameText = itemView.findViewById(R.id.actorRealName);
            actressImage = itemView.findViewById(R.id.actorPicture);
        }
    }
}
