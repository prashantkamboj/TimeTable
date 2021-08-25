package com.example.timetable.Fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.example.timetable.ToDoAdapter;
import com.example.timetable.helperClasses.ToDoData;
import com.example.timetable.helperClasses.ToDoTakingDialog;
import com.example.timetable.helperClasses.dbhelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ToDoFragment extends Fragment implements ToDoTakingDialog.CollectData {
     String dayName;
     RecyclerView recyclerView;
     ArrayList<ToDoData> dataList;
     FloatingActionButton floatingActionButton;
     TextView dayNameTextView;
    public ToDoFragment(String dayName) {
        this.dayName = dayName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.to_do_show_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.toDoRecyclerView);
        dayNameTextView = view.findViewById(R.id.timeTableDayShowTextView);
        dayNameTextView.setText(dayName);
        floatingActionButton= view.findViewById(R.id.addTimeTableButton);
        floatingActionButton.setOnClickListener(View->{
            ToDoTakingDialog dialog = new ToDoTakingDialog(ToDoFragment.this);
            dialog.show(getChildFragmentManager(),"ToDoTaking");
        });
        dataRetrive();
    }

    @Override
    public void collectData(ToDoData data){
        data.setDayname(dayName);
        dbhelper dbhelper = new dbhelper(this.getContext());
        dbhelper.getConnection();
        dbhelper.insertIntoToDo(data);
        dbhelper.closeConnection();
        dataRetrive();
    }
    @Override
    public void inform(){

    }

    @Override
    public void checkedChange(int id) {
        dbhelper dbhelper = new dbhelper(this.getContext());
        dbhelper.getConnection();
        dbhelper.deleteToDo(id);
        dbhelper.closeConnection();
        dataRetrive();
    //    Log.d("checkedChange", "checkedChange: Hum Chal Rahe HAi And This Is Id ="+id);
    }

    void dataRetrive(){
        dbhelper dbhelper = new dbhelper(this.getContext());
        dbhelper.getConnection();
        dataList = dbhelper.getToDoData(dayName);
        dbhelper.closeConnection();
        setAdapter(dataList);
      //  Log.d("Data Retrive ", "dataRetrive: Hum Bhi Chl Rhe Hai");
    }
    void setAdapter(ArrayList<ToDoData> dataList){
        recyclerView.setAdapter(new ToDoAdapter(dataList,ToDoFragment.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
