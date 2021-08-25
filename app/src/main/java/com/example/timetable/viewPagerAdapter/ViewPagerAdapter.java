package com.example.timetable.viewPagerAdapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.timetable.Fragments.FridayFragment;
import com.example.timetable.Fragments.MondayFragment;
import com.example.timetable.Fragments.SaturdayFragment;
import com.example.timetable.Fragments.SundayFragment;
import com.example.timetable.Fragments.ThursdayFragment;
import com.example.timetable.Fragments.TuesdayFragment;
import com.example.timetable.Fragments.WednesdayFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter{
    Context context;
    public ViewPagerAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
              return new SundayFragment(context);
            case 1:
                return new MondayFragment(context);
            case 2:
               return new TuesdayFragment(context);
                case 3:
                return new WednesdayFragment(context);
               case 4:
                return new ThursdayFragment(context);
                case 5:
                 return new FridayFragment(context);
                case 6:
                 return new SaturdayFragment(context);
                default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
