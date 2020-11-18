package com.HowIMetYourMother.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.MyViewHolder> {

    String actorsNames[];
    String actorsRealNames[];
    int actorsImage[];
    Context context;

    public ActorsAdapter(Context ct, String names[], String realNames[], int images[]){
        context = ct;
        actorsNames = names;
        actorsRealNames = realNames;
        actorsImage = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameText.setText(actorsNames[position]);
        holder.realNameText.setText(actorsRealNames[position]);
        holder.actorImage.setImageResource(actorsImage[position]);
    }

    @Override
    public int getItemCount() {
        return actorsImage.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, realNameText;
        ImageView actorImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.actorName);
            realNameText = itemView.findViewById(R.id.actorRealName);
            actorImage = itemView.findViewById(R.id.actorPicture);
        }
    }
}
