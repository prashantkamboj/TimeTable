package com.example.timetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Fragments.ToDoFragment;
import com.example.timetable.helperClasses.TimeTableData;
import com.example.timetable.helperClasses.ToDoData;
import com.example.timetable.helperClasses.dbhelper;

import java.util.ArrayList;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolders> {
    ArrayList<ToDoData> datalist;
    ToDoFragment toDoFragment;
    public ToDoAdapter(ArrayList<ToDoData> datalist,ToDoFragment toDoFragment){
        this.datalist= datalist;
        this.toDoFragment= toDoFragment;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         View view = inflater.inflate(R.layout.to_do_show_adapter_layout,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        holder.WorkTextView.setText(datalist.get(position).getWork());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView WorkTextView;
       CheckBox checkBox;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            WorkTextView = itemView.findViewById(R.id.todoTextView);
            checkBox = itemView.findViewById(R.id.toDoCheckBox);
            checkBox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    toDoFragment.checkedChange(datalist.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
