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

public class MyPagAndamStepsAdapter extends RecyclerView.Adapter<MyPagAndamStepsAdapter.ViewHolder>{

    List<MyPagAndamStepsData> myPagAndamStepsData;
    Context context;
    Context mContext;

    public MyPagAndamStepsAdapter(Context con,List<MyPagAndamStepsData> myPagAndamStepsData, pagandamSteps activity){
        this.myPagAndamStepsData = myPagAndamStepsData;
        this.context = activity;
        mContext=con;
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
        final MyPagAndamStepsData myPagAndamStepsDataList = myPagAndamStepsData.get(position);
        holder.textViewName.setText(myPagAndamStepsDataList.getMainName());
        holder.textViewDate.setText(myPagAndamStepsDataList.getMainDetails());
        Picasso.with(context.getApplicationContext())
                .load(myPagAndamStepsDataList.getMainImage())
                .into(holder.mainImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"This is the STEPS: " + myPagAndamStepsDataList.getCategoryID(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPagAndamStepsData.size();
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

