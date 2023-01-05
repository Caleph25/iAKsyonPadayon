package com.example.login_signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportHistoryAdapter extends RecyclerView.Adapter<ReportHistoryAdapter.ViewHolder> {
    private List<ReportHistoryListData> listdata;
    Context context;
    Context histoContext;
    public ReportHistoryAdapter(List<ReportHistoryListData> listdata, ReportHistory activity, Context context){
        this.listdata = listdata;
        this.context = activity;
        this.histoContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.history_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReportHistoryListData myReportData = listdata.get(position);
        final View rootView =((ReportHistory)histoContext).getWindow().getDecorView().findViewById(android.R.id.content);

        String evtType = myReportData.getReportsName();
        String status = myReportData.getStatus();
        String timeDate = myReportData.getTimedate();

        holder.textEvent.setText(evtType);
        holder.textStatus.setText(status);
        holder.textTimeDate.setText(timeDate);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textEvent;
        public TextView textStatus;
        public TextView textTimeDate;
        public RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textEvent = (TextView) itemView.findViewById(R.id.EventType);
            this.textStatus = (TextView) itemView.findViewById(R.id.Status);
            this.textTimeDate = (TextView) itemView.findViewById(R.id.TimeandDate);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout2);
        }
    }
}
