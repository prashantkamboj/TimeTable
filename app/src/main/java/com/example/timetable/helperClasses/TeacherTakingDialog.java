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

import com.example.timetable.Fragments.MainTeacherFragment;
import com.example.timetable.R;

public class TeacherTakingDialog extends DialogFragment {
    TextView teacherName , teacherSub,teacherPhone;
    Button saveButton,cancelButton;
    MainTeacherFragment mainTeacherFragment;
    public TeacherTakingDialog(MainTeacherFragment mainTeacherFragment){
        this.mainTeacherFragment = mainTeacherFragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater  inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.teacher_taking_dialog,null);
          teacherName = view.findViewById(R.id.hmSubField);
          teacherPhone = view.findViewById(R.id.hmdesField);
          teacherSub = view.findViewById(R.id.teacherSubjectField);
          saveButton = view.findViewById(R.id.savehmButton);
          cancelButton = view.findViewById(R.id.cancelhmButton);
        builder.setView(view);
        Dialog dialog = builder.create();
        if(getArguments()==null){
             saveButton.setOnClickListener(View->{
                 TeacherData data = new TeacherData();
                 data.setName(teacherName.getText().toString());
                 data.setPhoneNumber(teacherPhone.getText().toString());
                 data.setSubject(teacherSub.getText().toString());
                 dbhelper dbhelper = new dbhelper(getContext());
                 dbhelper.getConnection();
                 dbhelper.addTeacher(data);
                 dbhelper.closeConnection();
                 mainTeacherFragment.dataReterive();
                 dialog.dismiss();
             });
        }else {
            Bundle bundle = getArguments();
            teacherName.setText(bundle.getString("TeacherName"));
            teacherPhone.setText(bundle.getString("TeacherPhone"));
            teacherSub.setText(bundle.getString("TeacherSubject"));
            int id = bundle.getInt("id");
            saveButton.setOnClickListener(View -> {
                TeacherData data = new TeacherData();
                data.setName(teacherName.getText().toString());
                data.setPhoneNumber(teacherPhone.getText().toString());
                data.setSubject(teacherSub.getText().toString());
                data.setId(id);
                dbhelper dbhelper = new dbhelper(getContext());
                dbhelper.getConnection();
                dbhelper.updateTeacher(data);
                dbhelper.closeConnection();
                mainTeacherFragment.dataReterive();
                dialog.dismiss();
            });
        }
            cancelButton.setOnClickListener(View -> {
                dialog.dismiss();
            });

        return dialog;
    }
}
