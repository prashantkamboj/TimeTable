package com.example.timetable;

import static com.example.timetable.R.string.close;
import static com.example.timetable.R.string.open;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.timetable.Fragments.MainFargment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
   Toolbar toolbar;
   DrawerLayout drawerLayout;
   NavigationView navview;
   ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawerlayout);
       navview = findViewById(R.id.navView);
       toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, open, close);
       toggle.syncState();
      getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MainFargment(),"MainFragment").commit();

    }

}

