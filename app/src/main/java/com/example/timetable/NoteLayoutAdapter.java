package com.example.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Fragments.NoteMainFragment;
import com.example.timetable.helperClasses.NoteData;
import com.example.timetable.helperClasses.dbhelper;

import java.util.ArrayList;

public class NoteLayoutAdapter extends RecyclerView.Adapter<NoteLayoutAdapter.ViewHolders> {
    ArrayList<NoteData> noteList;
    Informchanges informchanges;
    Context  context;
    public NoteLayoutAdapter(ArrayList<NoteData> noteList, Context context, NoteMainFragment noteMainFragment){
          this.noteList= noteList;
          informchanges = (Informchanges) noteMainFragment;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.note_grid_layout,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
         holder.titleText.setText(noteList.get(position).getDescription());
         holder.descriptionText.setText(noteList.get(position).getTitle());
    }
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView titleText ,descriptionText;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleShowText);
            descriptionText = itemView.findViewById(R.id.descriptionShow);
            itemView.setOnLongClickListener(View->{
                PopupMenu popupMenu = new PopupMenu(context,itemView);
                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        informchanges.deleteData(noteList.get(getAdapterPosition()).getId());
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            });
            itemView.setOnClickListener(View ->{
                informchanges.showData(noteList.get(getAdapterPosition()));
            });
        }
    }
    public interface Informchanges{
       void deleteData(int id);
       void showData(NoteData note);
    }
}
