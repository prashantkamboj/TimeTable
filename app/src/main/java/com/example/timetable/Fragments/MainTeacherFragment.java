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
import com.example.timetable.TeacherLayoutAdapter;
import com.example.timetable.helperClasses.TeacherData;
import com.example.timetable.helperClasses.TeacherTakingDialog;
import com.example.timetable.helperClasses.dbhelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainTeacherFragment extends Fragment  implements  TeacherLayoutAdapter.TeacherDataChange{
    RecyclerView recyclerView;
    FloatingActionButton fab;
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
        fab = view.findViewById(R.id.addNoteButton);
        recyclerView = view.findViewById(R.id.noteShowRecyclerView);
       fab.setOnClickListener(View->{
           TeacherTakingDialog teacherTakingDialog= new TeacherTakingDialog(MainTeacherFragment.this);
           teacherTakingDialog.show(getChildFragmentManager(),"TeacherTakingDialog");
       });
       dataReterive();
    }
    public void dataReterive(){
        dbhelper dbhelper= new dbhelper(getContext());
        dbhelper.getConnection();
        ArrayList<TeacherData> teacherList= dbhelper.getTeachers();
        dbhelper.closeConnection();
        attachAdapter(teacherList);
    }
    void attachAdapter(ArrayList<TeacherData> teacherList){
        recyclerView.setAdapter(new TeacherLayoutAdapter(teacherList,MainTeacherFragment.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void delete(int id) {
     dbhelper dbhelper= new dbhelper(getContext());
     dbhelper.getConnection();
     dbhelper.deleteTeacher(id);
     dbhelper.closeConnection();
     dataReterive();
    }
    @Override
    public void edit(TeacherData teacherData) {
        //ToDo This Is To Be Done
        TeacherTakingDialog teacherTakingDialog= new TeacherTakingDialog(MainTeacherFragment.this);
        Bundle bundle = new Bundle();
        bundle.putString("TeacherName",teacherData.getName());
        bundle.putString("TeacherPhone",teacherData.getPhoneNumber());
        bundle.putString("TeacherSubject",teacherData.getSubject());
        bundle.putInt("id",teacherData.getId());
        teacherTakingDialog.setArguments(bundle);
        teacherTakingDialog.show(getChildFragmentManager(),"TeacherTakingDialog");
        dataReterive();
    }
}
