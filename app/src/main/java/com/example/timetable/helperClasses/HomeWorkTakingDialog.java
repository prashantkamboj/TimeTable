package com.example.timetable.helperClasses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.timetable.Fragments.MainHomeWorkFragment;
import com.example.timetable.R;

public class HomeWorkTakingDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    TextView hmdate;
    EditText hmdes,hmsub;
    Button saveButton ,cancelButton;
    MainHomeWorkFragment mainHomeWorkFragment;
    public HomeWorkTakingDialog(MainHomeWorkFragment mainHomeWorkFragment){
         this.mainHomeWorkFragment=mainHomeWorkFragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.home_work_taking_dialog,null);
        hmdes = view.findViewById(R.id.hmdesField);
        hmsub = view.findViewById(R.id.hmSubField);
        hmdate = view.findViewById(R.id.hmdateField);
        saveButton = view.findViewById(R.id.savehmButton);
        cancelButton = view.findViewById(R.id.cancelhmButton);
        builder.setView(view);
        builder.setCancelable(false);
        Dialog dialog = builder.create();
        if(getArguments()==null){
            saveButton.setOnClickListener(View->{
                HomeWorkData data = new HomeWorkData();
                data.setDescription(hmdes.getText().toString());
                data.setSubject(hmsub.getText().toString());
                data.setCompletionDate(hmdate.getText().toString());
                dbhelper dbhelper = new dbhelper(getContext());
                dbhelper.getConnection();
                dbhelper.addHomeWork(data);
                dbhelper.closeConnection();
                mainHomeWorkFragment.dataRetrive();
                dialog.dismiss();
            });
        }else {
            HomeWorkData data = new HomeWorkData();
            Bundle bundle = getArguments();
            hmdes.setText(bundle.getString("hmDescription"));
            hmdate.setText(bundle.getString("hmDate"));
            hmsub.setText(bundle.getString("hmSubject"));
            data.setId(bundle.getInt("id"));
            saveButton.setOnClickListener(View -> {
                data.setDescription(hmdes.getText().toString());
                data.setSubject(hmsub.getText().toString());
                data.setCompletionDate(hmdate.getText().toString());
                dbhelper dbhelper = new dbhelper(getContext());
                dbhelper.getConnection();
                dbhelper.updateHomeWork(data);
                dbhelper.closeConnection();
                mainHomeWorkFragment.dataRetrive();
                dialog.dismiss();
            });
        }
        hmdate.setOnClickListener(View->{
            HmDatePickingDialog datePickingDialog= new HmDatePickingDialog(HomeWorkTakingDialog.this);
            datePickingDialog.show(getChildFragmentManager(),"HmDatePickingDialog");
        });
        cancelButton.setOnClickListener(View->{
            dialog.dismiss();
        });

        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        hmdate.setText(dayOfMonth+"/"+month+"/"+year);
    }
}
