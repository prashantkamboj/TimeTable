package com.example.timetable.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.R;
import com.example.timetable.helperClasses.ExamData;
import com.example.timetable.helperClasses.ExamTakingDialog;
import com.example.timetable.helperClasses.dbhelper;
import com.example.timetable.viewPagerAdapter.ExamLayoutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainExamFragment extends Fragment implements ExamTakingDialog.InformDataSave , ExamLayoutAdapter.ExamAdapterCommunication {
    RecyclerView recyclerView ;
    FloatingActionButton addExamfab;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_main_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.noteShowRecyclerView);
        addExamfab= view.findViewById(R.id.addNoteButton);
        addExamfab.setOnClickListener(View->{
            ExamTakingDialog examTakingDialog= new ExamTakingDialog(MainExamFragment.this);
            examTakingDialog.show(getChildFragmentManager(),null);
        });
        dataRetrieve();
    }

    private void dataRetrieve() {
        dbhelper dbh = new dbhelper(getContext());
        dbh.getConnection();
       ArrayList<ExamData> examList =  dbh.getExamData();
       dbh.closeConnection();
       attachAdapter(examList);
    }

    private void attachAdapter(ArrayList<ExamData> examList) {
        recyclerView.setAdapter(new ExamLayoutAdapter(examList,MainExamFragment.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void inform() {
       dataRetrieve();
    }

    @Override
    public void informChanges() {
     dataRetrieve();
    }

    @Override
    public void deleteExam(int id) {
        dbhelper dbh = new dbhelper(getContext());
        dbh.getConnection();
        dbh.deleteExam(id);
        dbh.closeConnection();
        dataRetrieve();
    }

    @Override
    public void editExam(ExamData examData) {
        ExamTakingDialog examTakingDialog= new ExamTakingDialog(MainExamFragment.this);
        Bundle bundle = new Bundle();
        bundle.putString("Place",examData.getExamPlace());
        bundle.putString("Name",examData.getExamName());
        bundle.putString("Date",examData.getExamDate());
        bundle.putInt("id",examData.getId());
        examTakingDialog.setArguments(bundle);
        examTakingDialog.show(getChildFragmentManager(),null);
    }
}
