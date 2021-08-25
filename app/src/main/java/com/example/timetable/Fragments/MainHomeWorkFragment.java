package com.example.timetable.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.R;
import com.example.timetable.helperClasses.HomeWorkData;
import com.example.timetable.helperClasses.HomeWorkTakingDialog;
import com.example.timetable.helperClasses.dbhelper;
import com.example.timetable.viewPagerAdapter.HomeWorkLayoutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainHomeWorkFragment extends Fragment implements HomeWorkLayoutAdapter.InformHomeWorkChanges {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_main_fragment,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.noteShowRecyclerView);
        fab = view.findViewById(R.id.addNoteButton);
        fab.setOnClickListener(View->{
            HomeWorkTakingDialog homeWorkTakingDialog = new HomeWorkTakingDialog(MainHomeWorkFragment.this);
            homeWorkTakingDialog.show(getChildFragmentManager(),"HomeWorkTakingDialog");
        });
        dataRetrive();
    }
    public void dataRetrive(){
        ArrayList<HomeWorkData> arrayList;
        dbhelper dbhelper = new dbhelper(getContext());
        dbhelper.getConnection();
        arrayList = dbhelper.getHomeWorkData();
        dbhelper.closeConnection();
        attachAdapter(arrayList);
        }
    void attachAdapter(ArrayList<HomeWorkData> arrayList) {
         recyclerView.setAdapter(new HomeWorkLayoutAdapter(arrayList,MainHomeWorkFragment.this));
        Log.d("Attach Adapter", "attachAdapter: Running");
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void inform() {
     dataRetrive();
    }

    @Override
    public void delete(int id) {
        dbhelper dbhelper = new dbhelper(getContext());
        dbhelper.getConnection();
        dbhelper.deleteHomeWork(id);
       dbhelper.closeConnection();
       dataRetrive();
    }

    @Override
    public void update(HomeWorkData data) {
        HomeWorkTakingDialog homeWorkTakingDialog = new HomeWorkTakingDialog(MainHomeWorkFragment.this);
        Bundle  bundle = new Bundle();
        bundle.putString("hmDescription",data.getDescription());
        bundle.putString("hmDate",data.getCompletionDate());
        bundle.putString("hmSubject",data.getSubject());
        bundle.putInt("id",data.getId());
        homeWorkTakingDialog.setArguments(bundle);
        homeWorkTakingDialog.show(getChildFragmentManager(),"HomeWorkTakingDialog");

    }
}
