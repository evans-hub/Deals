package com.example.deals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {
    Context context;
    ArrayList<Entity> lists;

    public MainAdapter(Context context, ArrayList<Entity> lists) {
        this.context = context;
        this.lists = lists;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Entity entity=lists.get(position);
        holder.review.setText(entity.getRatings());
        holder.location.setText(entity.getLocation());
        holder.title.setText(entity.getTitle());
        holder.pay.setText("$"+entity.getPay()+"/Day");
        Picasso.get().load(entity.getImage()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(holder.carimage);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title,location,review,pay;
        ImageView carimage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.list_car_title);
            location=itemView.findViewById(R.id.car_list_location);
            review=itemView.findViewById(R.id.list_car_reviews);
            pay=itemView.findViewById(R.id.list_pay);
            carimage=itemView.findViewById(R.id.list_image);
        }
    }
}
