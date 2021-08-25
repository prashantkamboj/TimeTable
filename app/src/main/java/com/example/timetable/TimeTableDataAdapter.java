package com.example.timetable;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Fragments.TimeTableFragment;
import com.example.timetable.helperClasses.TimeTableData;
import com.example.timetable.helperClasses.TimeTableTakingDialog;
import com.example.timetable.helperClasses.dbhelper;

import java.util.ArrayList;

public class TimeTableDataAdapter extends RecyclerView.Adapter<TimeTableDataAdapter.ViewHolders>{
    ArrayList<TimeTableData> dataList;
    Context context;
    TimeTableFragment timeTableFragment;
    public TimeTableDataAdapter(ArrayList<TimeTableData> dataList,Context context,TimeTableFragment timeTableFragment)
    {
        this.dataList = dataList;
        this.context = context;
        this.timeTableFragment= timeTableFragment;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.time_table_show_view,parent,false);
        return new ViewHolders(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position){
        holder.work.setText(dataList.get(position).getWork());
        holder.fromTime.setText("From Time  ->  "+dataList.get(position).getFromTime());
        holder.toTime.setText("To Time   ->  "+dataList.get(position).getToTime());
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView work,fromTime,toTime;
        public ViewHolders(@NonNull View itemView)
        {
            super(itemView);
            work= itemView.findViewById(R.id.workDescribeTextView);
            fromTime  = itemView.findViewById(R.id.fromTimeTextView);
            toTime = itemView.findViewById(R.id.toTimeTextView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, itemView);
                    popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            dbhelper dbhelper = new dbhelper(context);
                            dbhelper.getConnection();
                            dbhelper.deleteTTItem(dataList.get(getAdapterPosition()).getId());
                            dataList.remove(getAdapterPosition());
                            dbhelper.closeConnection();
                            timeTableFragment.inform();
                            return false;
                        }
                    });
                    popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            TimeTableTakingDialog dialog = new TimeTableTakingDialog(timeTableFragment);
                            Bundle bundle = new Bundle();
                            TimeTableData data = dataList.get(getAdapterPosition());
                            bundle.putString("work",data.getWork());
                            bundle.putString("fromTime",data.getFromTime());
                            bundle.putString("toTime",data.getToTime());
                            bundle.putInt("id",data.getId());
                            bundle.putString("dayName",data.getDayName());
                            dialog.setArguments(bundle);
                            dialog.show(timeTableFragment.getChildFragmentManager(), "Edit Dialog");
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
        }
    }
}
