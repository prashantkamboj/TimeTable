package com.example.timetable.helperClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.timetable.R;

import java.util.Calendar;

public class TimeTableTakingDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    DataCollector dataCollector;
    EditText worktaking,fromtime,toTimeField;
    public TimeTableTakingDialog(DataCollector dataCollector){
        this.dataCollector = dataCollector;
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
        View view = inflater.inflate(R.layout.time_table_taking_view, null);
        worktaking = view.findViewById(R.id.getWork);
        fromtime = view.findViewById(R.id.getFromTime);
        toTimeField = view.findViewById(R.id.getTotime);
        Button cancel = view.findViewById(R.id.canceldialog);
        Button save = view.findViewById(R.id.saveDialog);
        fromtime.setOnClickListener(View->{
           TimePickerDialog dialog = new TimePickerDialog(getContext(),TimeTableTakingDialog.this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                   Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
           dialog.show();
        });
        toTimeField.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(getContext(),new ToTimeFieldClass(), Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
            dialog.show();
        });
        builder.setView(view);
        Dialog dialog = builder.create();
        if (getArguments()==null) {
            cancel.setOnClickListener(View -> dialog.dismiss());
            save.setOnClickListener(View -> {
                String work = worktaking.getText().toString();
                String fromTime = fromtime.getText().toString();
                String ToTime = toTimeField.getText().toString();
                if (work.isEmpty()) {
                    worktaking.setError("This Field Is Required");
                } else if (fromTime.isEmpty()) {
                    fromtime.setError("This Field Is Required");
                } else if (ToTime.isEmpty()) {
                    toTimeField.setError("This Field Is Required");
                } else {
                    TimeTableData data = new TimeTableData();
                    data.setWork(work);
                    data.setFromTime(fromTime);
                    data.setToTime(ToTime);
                    dataCollector.collecData(data);
                    dialog.dismiss();
                }
            });
        }else{
            Bundle bundle = getArguments();
            worktaking.setText(bundle.getString("work"));
            fromtime.setText(bundle.getString("fromTime"));
            toTimeField.setText(bundle.getString("toTime"));
            cancel.setOnClickListener(View -> dialog.dismiss());
            save.setOnClickListener(View -> {
                String work = worktaking.getText().toString();
                String fromTime = fromtime.getText().toString();
                String ToTime = toTimeField.getText().toString();
                if (work.isEmpty()) {
                    worktaking.setError("This Field Is Required");
                } else if (fromTime.isEmpty()) {
                    fromtime.setError("This Field Is Required");
                } else if (ToTime.isEmpty()) {
                    toTimeField.setError("This Field Is Required");
                } else {
                    TimeTableData data = new TimeTableData();
                    data.setWork(work);
                    data.setId(bundle.getInt("id"));
                    data.setDayName(bundle.getString("dayName"));
                    data.setFromTime(fromTime);
                    data.setToTime(ToTime);
                    dataCollector.informEdit(data);
                    dialog.dismiss();
                }

            });
        }
            return dialog;

    }
    class ToTimeFieldClass implements TimePickerDialog.OnTimeSetListener
    {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = hourOfDay+":"+minute;
              toTimeField.setText(time);
        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay+":"+minute;
        fromtime.setText(time);
    }

    public interface DataCollector{
        void collecData(TimeTableData data);
        void informEdit(TimeTableData data);
        void inform();
    }
}
