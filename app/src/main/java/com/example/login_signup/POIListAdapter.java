package com.example.login_signup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class POIListAdapter extends RecyclerView.Adapter<POIListAdapter.ViewHolder>{
    private List<POIListData> listdata;
    Context context;
    Context mapContext;
    // RecyclerView recyclerView;
    public POIListAdapter(List<POIListData> listdata, paglikas activity,Context context) {
        this.listdata = listdata;
        this.context = activity;
        this.mapContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.poi_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final POIListData myListData = listdata.get(position);
        final View rootView = ((paglikas)mapContext).getWindow().getDecorView().findViewById(android.R.id.content);
        TextView xCurrentTV = (TextView)rootView.findViewById(R.id.xCurrent);
        TextView yCurrentTV = (TextView)rootView.findViewById(R.id.yCurrent);

        float xCurrentDistance=listdata.get(position).getxLocationCurrent();
        float yCurrentDistance=listdata.get(position).getyLocationCurrent();

        float xDestination= Float.parseFloat(listdata.get(position).getxLocation());
        float yDestination= Float.parseFloat(listdata.get(position).getyLocation());


        LatLng Currentlocation = new LatLng(xCurrentDistance, yCurrentDistance);
        LatLng Destinationlocation = new LatLng(xDestination, yDestination);
        Double distance = SphericalUtil.computeDistanceBetween(Currentlocation, Destinationlocation);
        if(distance!=null){
            holder.textViewDistance.setText(String.format("%.2f", distance / 1000) + "km");
            listdata.get(position).setDistance(Float.parseFloat(String.format("%.2f", distance / 1000)));
        }
        holder.textView.setText(listdata.get(position).getDescription() + "Distance" + listdata.get(position).getDistance());
        String POIName = listdata.get(position).getDescription();
        String POIImage=listdata.get(position).getImgId();
        //Need Current Location of the User
        Picasso.with(context.getApplicationContext())
                .load(listdata.get(position).getImgId())
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof paglikas) {
                    float xCurrent=Float.parseFloat( xCurrentTV.getText().toString());
                    float yCurrent=Float.parseFloat( yCurrentTV.getText().toString());
                    ((paglikas)context).setRoute(xCurrent,yCurrent,xDestination,yDestination,POIName,POIImage);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textViewDistance;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textViewDistance= (TextView) itemView.findViewById(R.id.textViewDistance);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}