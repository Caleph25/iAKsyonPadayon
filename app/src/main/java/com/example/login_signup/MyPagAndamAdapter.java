package com.example.login_signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPagAndamAdapter extends RecyclerView.Adapter<MyPagAndamAdapter.ViewHolder>{

    MyPagAndamData[] myPagAndamData;
    Context context;

    public MyPagAndamAdapter(MyPagAndamData[] myPagAndamData, pagandam activity){
        this.myPagAndamData = myPagAndamData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pagandam_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyPagAndamData myPagAndamDataList = myPagAndamData[position];
        holder.textViewName.setText(myPagAndamDataList.getMovieName());
        holder.textViewDate.setText(myPagAndamDataList.getMovieDate());
        holder.movieImage.setImageResource(myPagAndamDataList.getMovieImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, myPagAndamDataList.getMovieName(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return myPagAndamData.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imageview);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textdate);


        }
    }


}
