package com.example.timetable.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.timetable.R;
import com.example.timetable.viewPagerAdapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Date;

public class MainFargment extends Fragment {
    ViewPager mainViewPager;
    TabLayout mainTabLayout;
   Context context;
    public MainFargment(Context context){
       this.context= context;

   }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewPager = view.findViewById(R.id.mainViewPager);
        mainTabLayout =view.findViewById(R.id.mainTabLayout);
        mainTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        ViewPagerAdapter adapter =new ViewPagerAdapter(getChildFragmentManager(),context);
        mainViewPager.setAdapter(adapter);
        Date date = new Date();
        mainViewPager.setCurrentItem(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1);
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewPager.setCurrentItem(tab.getPosition());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTabLayout));
    }
}
