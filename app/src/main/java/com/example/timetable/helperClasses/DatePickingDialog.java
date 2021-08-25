package com.example.timetable.helperClasses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickingDialog extends DialogFragment {
    ExamTakingDialog examTakingDialog;
    public DatePickingDialog(ExamTakingDialog examTakingDialog){
        this.examTakingDialog = examTakingDialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month  = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),examTakingDialog,year,month,day);
        return datePickerDialog;
    }
}