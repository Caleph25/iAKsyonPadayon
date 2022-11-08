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

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPagAndamSubAdapter extends RecyclerView.Adapter<MyPagAndamSubAdapter.ViewHolder>{

    List<MyPagAndamSubData> myPagAndamSubData;
    Context context;

    public MyPagAndamSubAdapter(List<MyPagAndamSubData> myPagAndamSubData, pagandamSub activity){
        this.myPagAndamSubData = myPagAndamSubData;
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
        final MyPagAndamSubData myPagAndamSubDataList = myPagAndamSubData.get(position);
        holder.textViewName.setText(myPagAndamSubDataList.getMainName());
        holder.textViewDate.setText(myPagAndamSubDataList.getMainDetails());
        Picasso.with(context.getApplicationContext())
                .load(myPagAndamSubDataList.getMainImage())
                .into(holder.mainImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return myPagAndamSubData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImage;
        TextView textViewName;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.imageview);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textdetails);


        }
    }


}
