package com.example.timetable.viewPagerAdapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Fragments.MainHomeWorkFragment;
import com.example.timetable.R;
import com.example.timetable.helperClasses.HomeWorkData;

import java.util.ArrayList;

public class HomeWorkLayoutAdapter extends RecyclerView.Adapter<HomeWorkLayoutAdapter.ViewHolders> {
    ArrayList<HomeWorkData> dataList;
    InformHomeWorkChanges informHomeWorkChanges;
    public HomeWorkLayoutAdapter(ArrayList<HomeWorkData> dataList, MainHomeWorkFragment homeWorkFragment){
        this.dataList = dataList;
        informHomeWorkChanges = (InformHomeWorkChanges) homeWorkFragment;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_work_adapter_layout,parent,false);
        return new ViewHolders(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
         holder.hmSub.setText(dataList.get(position).getSubject());
         holder.hmdes.setText(dataList.get(position).getDescription());
         holder.hmDate.setText(dataList.get(position).getCompletionDate());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolders extends  RecyclerView.ViewHolder{
       TextView hmdes ,hmDate,hmSub;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            hmdes = itemView.findViewById(R.id.hmWorkDescription);
            hmDate = itemView.findViewById(R.id.hmWorkDate);
            hmSub = itemView.findViewById(R.id.homeWorkSubject);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu menu = new PopupMenu(itemView.getContext(),itemView);
                    menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            informHomeWorkChanges.delete(dataList.get(getAdapterPosition()).getId());
                            return false;
                        }
                    });
                    menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            informHomeWorkChanges.update(dataList.get(getAdapterPosition()));
                            return false;
                        }
                    });
                    menu.show();
                    return false;
                }
            });
        }
    }
   public interface InformHomeWorkChanges {
        void inform();
        void delete(int id );
        void update(HomeWorkData data);
    }
}
