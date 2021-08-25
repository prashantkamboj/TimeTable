package com.example.timetable.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timetable.ForCommunication;
import com.example.timetable.MainActivity;
import com.example.timetable.R;
import com.example.timetable.helperClasses.NoteData;
import com.example.timetable.helperClasses.dbhelper;

public class NoteAddFragment extends Fragment {
    Button saveButton, cancelButton;
    EditText titleText, descriptionText;
    ForCommunication forCommunication;
    Context context;

    public NoteAddFragment(MainActivity mainActivity, Context context) {
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
        return inflater.inflate(R.layout.note_add_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton = view.findViewById(R.id.notesaveButton);
        cancelButton = view.findViewById(R.id.notecancelButton);
        titleText = view.findViewById(R.id.noteTitleTextView);
        descriptionText = view.findViewById(R.id.noteDescriptionTextView);
        cancelButton.setOnClickListener(v -> {
            forCommunication.onBackButton();
        });
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            titleText.setText(bundle.getString("title"));
            descriptionText.setText(bundle.getString("description"));
            int id = bundle.getInt("id");
            saveButton.setOnClickListener(v ->
            {
                if (titleText.getText().toString().isEmpty() || descriptionText.getText().toString().isEmpty()) {
                    if (titleText.getText().toString().isEmpty()) {
                        titleText.setError("This Field Is Required");
                    } else {
                        descriptionText.setError("This Field Is Required");
                    }
                } else {
                    NoteData note = new NoteData();
                    note.setTitle(titleText.getText().toString());
                    note.setDescription(descriptionText.getText().toString());
                    note.setId(id);
                    dbhelper dhelper = new dbhelper(getContext());
                    dhelper.getConnection();
                    dhelper.updateNote(note);
                    dhelper.closeConnection();
                    forCommunication.onBackButton();
                }
            });
        } else {
            saveButton.setOnClickListener(v ->
            {
                if (titleText.getText().toString().isEmpty() || descriptionText.getText().toString().isEmpty()) {
                    if (titleText.getText().toString().isEmpty()) {
                        titleText.setError("This Field Is Required");
                    } else {
                        descriptionText.setError("This Field Is Required");
                    }
                } else {
                    NoteData note = new NoteData();
                    note.setTitle(titleText.getText().toString());
                    note.setDescription(descriptionText.getText().toString());
                    dbhelper dhelper = new dbhelper(getContext());
                    dhelper.getConnection();
                    dhelper.addNote(note);
                    dhelper.closeConnection();
                    forCommunication.onBackButton();
                }
            });
        }
    }
}
