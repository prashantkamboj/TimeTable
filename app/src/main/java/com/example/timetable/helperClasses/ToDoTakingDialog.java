package com.example.timetable.helperClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.timetable.R;

public class ToDoTakingDialog extends DialogFragment {
    TextView todowork;
    Button saveButton,cancelButton;
    CollectData collectData;
    public ToDoTakingDialog(CollectData collectData){
        this.collectData = collectData;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog;
        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.to_do_taking_view,null);
        todowork = view.findViewById(R.id.toDoWorkAdd);
        saveButton = view.findViewById(R.id.saveToDoButton);
        cancelButton = view.findViewById(R.id.cancelToDoButton);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoData data = new ToDoData();
                String work = todowork.getText().toString();
                data.setWork(work);
                collectData.collectData(data);
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
    public interface CollectData{
    void collectData(ToDoData data);
    void inform();
    void checkedChange(int id);
    }
}
