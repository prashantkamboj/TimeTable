package com.example.timetable.helperClasses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.timetable.Fragments.MainExamFragment;
import com.example.timetable.R;

import java.util.Calendar;

public class ExamTakingDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
      Button saveButton,canceButton;
      EditText addExam ,addPlace;
      TextView addDate;
      InformDataSave informDataSave;
     public ExamTakingDialog(MainExamFragment mainExamFragment){
          informDataSave =(InformDataSave) mainExamFragment;
      }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.exam_add_dialog, null, false);
            addExam = view.findViewById(R.id.examNameAdd);
            addDate = view.findViewById(R.id.examDateAdd);
            addPlace = view.findViewById(R.id.examPlaceAdd);
            saveButton = view.findViewById(R.id.examAddSaveButton);
            canceButton = view.findViewById(R.id.examAddCancelButton);
            builder.setView(view);
            builder.setCancelable(false);
            Dialog dialog = builder.create();
        if (getArguments() != null) {
               Bundle bundle = getArguments();
               addExam.setText(bundle.getString("Name"));
               addDate.setText(bundle.getString("Date"));
               addPlace.setText(bundle.getString("Place"));
                int id = bundle.getInt("id");
            saveButton.setOnClickListener(View -> {
                ExamData data = new ExamData();
                data.setExamName(addExam.getText().toString());
                data.setExamPlace(addPlace.getText().toString());
                data.setExamDate(addDate.getText().toString());
                data.setId(id);
                dbhelper dbh = new dbhelper(getContext());
                dbh.getConnection();
                dbh.updateExam(data);
                dbh.closeConnection();
                informDataSave.inform();
                dialog.dismiss();
            });
        }else {
            saveButton.setOnClickListener(View -> {
                ExamData data = new ExamData();
                data.setExamName(addExam.getText().toString());
                data.setExamPlace(addPlace.getText().toString());
                data.setExamDate(addDate.getText().toString());
                dbhelper dbh = new dbhelper(getContext());
                dbh.getConnection();
                dbh.addExam(data);
                dbh.closeConnection();
                informDataSave.inform();
                dialog.dismiss();
            });
        }
            canceButton.setOnClickListener(View -> {
                dialog.dismiss();
            });
            addDate.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    DatePickingDialog datePickingDialog = new DatePickingDialog(ExamTakingDialog.this);
                    datePickingDialog.show(getChildFragmentManager(), "datePickingDialog");
                }
            });

        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
             addDate.setText(dayOfMonth+"/"+month+"/"+year);
    }

    public interface InformDataSave{
          void inform();
    }

}
