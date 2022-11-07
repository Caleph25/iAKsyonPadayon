package com.example.login_signup;

import android.content.Context;
import android.content.Intent;
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

public class MyPagAndamAdapter extends RecyclerView.Adapter<MyPagAndamAdapter.ViewHolder>{

    List<MyPagAndamData> myPagAndamData;
    Context context;
    Context mContext;

    public MyPagAndamAdapter(List<MyPagAndamData> myPagAndamData, pagandam activity){
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
        final MyPagAndamData myPagAndamDataList = myPagAndamData.get(position);
        holder.textViewName.setText(myPagAndamDataList.getMainName());
        holder.textViewDate.setText(myPagAndamDataList.getMainDetails());
        Picasso.with(context.getApplicationContext())
                .load(myPagAndamDataList.getMainImage())
                .into(holder.mainImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call kayo ng New Activity dito na parang pagandam.java din anfg layout
                Toast.makeText(context,"This is the MainCategoryID: " + myPagAndamDataList.getCategoryID(), Toast.LENGTH_SHORT).show();
                //Create intent getting the context of your View and the class where you want to go
                Intent intent = new Intent(v.getContext(), pagandamSub.class);

                //start the activity from the view/context
                v.getContext().startActivity(intent); //If you are inside activity, otherwise pass context to this funtion
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPagAndamData.size();
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

