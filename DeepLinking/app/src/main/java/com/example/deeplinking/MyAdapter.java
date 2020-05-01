package com.example.deeplinking;

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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Context context;
    ArrayList<Contents> contents;

    public MyAdapter(Context context, ArrayList<Contents> contents) {
        this.context = context;
        this.contents = contents;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent, false);
        MyHolder holder = new MyHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(contents.get(position).getName());
        PicassoClient.downloadImage(context, CloudinaryClient.getRoundCornerImage(contents.get(position).getUrl()),holder.img);
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.nameTxt);

        }
    }
}
