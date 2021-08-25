package com.example.timetable.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.timetable.ForCommunication;
import com.example.timetable.MainActivity;
import com.example.timetable.NoteLayoutAdapter;
import com.example.timetable.R;
import com.example.timetable.helperClasses.NoteData;
import com.example.timetable.helperClasses.dbhelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoteMainFragment extends Fragment implements NoteLayoutAdapter.Informchanges {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Context context;
    ForCommunication forCommunication;
    public NoteMainFragment(MainActivity mainActivity, Context context){
        this.context = context;
        forCommunication = (ForCommunication) mainActivity;
    }
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
        recyclerView =view.findViewById(R.id.noteShowRecyclerView);
        fab=view.findViewById(R.id.addNoteButton);
        fab.setOnClickListener(View->{
            forCommunication.noteFragmentChanger();
        });
        dataRetrive();
    }
     void dataRetrive(){
         dbhelper dhelper = new dbhelper(getContext());
         dhelper.getConnection();
       ArrayList<NoteData> noteList=  dhelper.getNoteData();
       dhelper.closeConnection();
          attachAdapter(noteList);
     }
     void attachAdapter(ArrayList<NoteData> noteList){
       recyclerView.setAdapter(new NoteLayoutAdapter(noteList,this.getContext(),NoteMainFragment.this));
       recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void deleteData(int id) {
          dbhelper dhelper = new dbhelper(getContext());
          dhelper.getConnection();
          dhelper.deleteNote(id);
          dhelper.closeConnection();
          dataRetrive();
    }

    @Override
    public void showData(NoteData note) {
       forCommunication.noteShow(note);
    }

}
