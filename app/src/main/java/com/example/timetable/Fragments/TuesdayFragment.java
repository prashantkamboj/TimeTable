package com.example.timetable.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timetable.ForCommunication;
import com.example.timetable.R;

public class TuesdayFragment extends Fragment {
    ImageView timerView,todoView;
    TextView textView ;
    ForCommunication forCommunication;
    Context context;
    final String dayname = "Tuesday";
    public TuesdayFragment(Context context){
        this.context = context;
        forCommunication =(ForCommunication)context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.monday_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timerView = view.findViewById(R.id.timerButton);
        todoView = view.findViewById(R.id.todoButton);
        timerView.setOnClickListener(View->{
            forCommunication.goToTimeTable(dayname);
        });
        todoView.setOnClickListener(View->{
            forCommunication.goToToDoList(dayname);
        });
    }
}
