package com.example.timetable;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Fragments.MainTeacherFragment;
import com.example.timetable.helperClasses.TeacherData;

import java.util.ArrayList;

public class TeacherLayoutAdapter extends RecyclerView.Adapter<TeacherLayoutAdapter.ViewHolders> {
    ArrayList<TeacherData> teacherList;
    TeacherDataChange dataChange;
    public TeacherLayoutAdapter(ArrayList<TeacherData> teacherList, MainTeacherFragment dataChange){
        this.teacherList = teacherList;
       this.dataChange = (TeacherDataChange) dataChange;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.teacher_layout_adapter,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        holder.teacherName.setText(teacherList.get(position).getName());
        holder.teacherSub.setText(teacherList.get(position).getSubject());
        holder.teacherPhone.setText(teacherList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView teacherName, teacherSub,teacherPhone;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.teacherName);
            teacherPhone = itemView.findViewById(R.id.hmWorkDescription);
            teacherSub = itemView.findViewById(R.id.hmWorkDate);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu menu = new PopupMenu(itemView.getContext(), itemView);
                    menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item){
                           dataChange.delete(teacherList.get(getAdapterPosition()).getId());
                            return false;
                        }
                    });
                    menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            dataChange.edit(teacherList.get(getAdapterPosition()));
                            return false;
                        }
                    });
                    menu.show();
                    return false;
                }
            });
        }
    }
   public interface TeacherDataChange{
        void delete(int id);

        //ToDo Handle The Edit here
        void  edit(TeacherData teacherData);
    }
}
