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

    public MyPagAndamAdapter(Context con,List<MyPagAndamData> myPagAndamData, pagandam activity){
        this.myPagAndamData = myPagAndamData;
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
        final MyPagAndamData myPagAndamDataList = myPagAndamData.get(position);
        holder.textViewName.setText(myPagAndamDataList.getMainName());
        //holder.textViewDate.setText(myPagAndamDataList.getMainDetails());
        Picasso.with(context.getApplicationContext())
                .load(myPagAndamDataList.getMainImage())
                .into(holder.mainImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"This is the MainCategoryID: " + myPagAndamDataList.getCategoryID(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(),pagandamSub.class);
                String MainCat= String.valueOf(myPagAndamDataList.getCategoryID());
                intent.putExtra("MainCategoryID", MainCat.toString() );
                mContext.startActivity(intent);

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
        //TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.imageview);
            textViewName = itemView.findViewById(R.id.textName);
            //textViewDate = itemView.findViewById(R.id.textdetails);


        }
    }

}

