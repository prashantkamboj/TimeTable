package com.example.timetable.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.R;
import com.example.timetable.TimeTableDataAdapter;
import com.example.timetable.helperClasses.TimeTableData;
import com.example.timetable.helperClasses.TimeTableTakingDialog;
import com.example.timetable.helperClasses.dbhelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TimeTableFragment extends Fragment implements TimeTableTakingDialog.DataCollector{
    String dayName;
    TextView dayNameTextView;
    FloatingActionButton fab;
    TimeTableData data;
    RecyclerView recyclerView;
    public TimeTableFragment(String dayName){
    this.dayName= dayName;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_table_show_fragment,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dayNameTextView = view.findViewById(R.id.weekLabelTextView);
        dayNameTextView.setText(dayName);
        fab = view.findViewById(R.id.addTimeTableButton);
        recyclerView = view.findViewById(R.id.toDoRecyclerView);
        dataRetrive();
        fab.setOnClickListener(View-> new TimeTableTakingDialog(TimeTableFragment.this).show(getChildFragmentManager(),"timeTableTaking"));
    }
    @Override
    public void collecData(TimeTableData data) {
        this.data = data;
     data.setDayName(dayName);
        dbhelper dbhelp = new dbhelper(this.getContext());
        dbhelp.getConnection();
        dbhelp.insertTTWork(data);
        dbhelp.closeConnection();
        dataRetrive();
    }

    @Override
    public void informEdit(TimeTableData data){
        dbhelper dbhelp = new dbhelper(this.getContext());
        dbhelp.getConnection();
        dbhelp.update(data);
        dbhelp.closeConnection();
        dataRetrive();
    }

    @Override
    public void inform() {
     dataRetrive();
    }

    void dataRetrive(){
        dbhelper dbhelp = new dbhelper(this.getContext());
        dbhelp.getConnection();
        ArrayList<TimeTableData> dataList =dbhelp.getTimeTableData(dayName);
        adapterSet(dataList);
    }
    void adapterSet(ArrayList<TimeTableData> dataList){
        recyclerView.setAdapter(new TimeTableDataAdapter(dataList,this.getContext(),this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}
