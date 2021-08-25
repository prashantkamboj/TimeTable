package com.example.timetable.viewPagerAdapter;

import android.content.ContentValues;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Fragments.MainExamFragment;
import com.example.timetable.R;
import com.example.timetable.helperClasses.ExamData;

import java.util.ArrayList;

public class ExamLayoutAdapter extends RecyclerView.Adapter<ExamLayoutAdapter.ViewHolders> {
    ArrayList<ExamData> examList;
    ExamAdapterCommunication communicationInterface;
       public ExamLayoutAdapter(ArrayList<ExamData> examList,MainExamFragment mainExamFragment){
           this.examList = examList;
           communicationInterface = (ExamAdapterCommunication) mainExamFragment;
       }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.exam_adapter_layout,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
           holder.examName.setText(examList.get(position).getExamName());
           holder.examPlace.setText(examList.get(position).getExamPlace());
           holder.examDate.setText(examList.get(position).getExamDate());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }
    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView examName,examDate,examPlace;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            examDate = itemView.findViewById(R.id.examDateAdapterView);
            examName = itemView.findViewById(R.id.examNameAdapterView);
            examPlace = itemView.findViewById(R.id.placeShowAdapterView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu menu = new PopupMenu(itemView.getContext(),itemView);
                    menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            communicationInterface.deleteExam(examList.get(getAdapterPosition()).getId());
                            return false;
                        }
                    });
                    menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            communicationInterface.editExam(examList.get(getAdapterPosition()));
                            return false;
                        }
                    });
                    menu.show();

                    return false;
                }
            });
        }
    }
    public interface ExamAdapterCommunication{
        void informChanges();
        void deleteExam(int id);
        void editExam(ExamData examData);
    }
}
