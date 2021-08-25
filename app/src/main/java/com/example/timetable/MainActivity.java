package com.example.timetable;

import static com.example.timetable.R.string.close;
import static com.example.timetable.R.string.open;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.timetable.Fragments.MainExamFragment;
import com.example.timetable.Fragments.MainFargment;
import com.example.timetable.Fragments.MainHomeWorkFragment;
import com.example.timetable.Fragments.MainTeacherFragment;
import com.example.timetable.Fragments.NoteAddFragment;
import com.example.timetable.Fragments.NoteMainFragment;
import com.example.timetable.Fragments.TimeTableFragment;
import com.example.timetable.Fragments.ToDoFragment;
import com.example.timetable.helperClasses.ExamTakingDialog;
import com.example.timetable.helperClasses.NoteData;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements ForCommunication {
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
       navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {    //TODO: Handle The NavigationItem Clicked
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch(item.getItemId()){
                   case R.id.notes:
                       NoteMainFragment fragment = (NoteMainFragment) getSupportFragmentManager().findFragmentByTag("NoteMainFragment");
                       if(fragment!=null && fragment.isVisible()){
                           drawerLayout.closeDrawer(GravityCompat.START,true);
                       }else {
                             drawerLayout.closeDrawer(GravityCompat.START,true);
                           getSupportFragmentManager().popBackStack();
                           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NoteMainFragment(MainActivity.this, getApplicationContext()),
                                   "NoteMainFragment").addToBackStack(null).commit();
                       }
                       break;
                   case R.id.exam:
                       MainExamFragment mainExam = (MainExamFragment) getSupportFragmentManager().findFragmentByTag("MainExamFragment");
                       if(mainExam!=null && mainExam.isVisible()){
                           drawerLayout.closeDrawer(GravityCompat.START,true);
                       }
                       else {
                           //Toast.makeText(getApplicationContext(), "This Feature is Comming Soon", Toast.LENGTH_SHORT).show();
                           drawerLayout.closeDrawer(GravityCompat.START,true);
                           getSupportFragmentManager().popBackStack();
                           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MainExamFragment(), "MainExamFragment")
                                   .addToBackStack(null)
                                   .commit();
                       }
                       break;
                   case R.id.teachers:
                       MainTeacherFragment mainTeacher = (MainTeacherFragment) getSupportFragmentManager().findFragmentByTag("MainTeacherFragment");
                       if(mainTeacher!=null && mainTeacher.isVisible()){
                           drawerLayout.closeDrawer(GravityCompat.START,true);
                       }
                       else {
                           //Toast.makeText(getApplicationContext(), "This Feature is Comming Soon", Toast.LENGTH_SHORT).show();
                           drawerLayout.closeDrawer(GravityCompat.START,true);
                           getSupportFragmentManager().popBackStack();
                           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MainTeacherFragment(), "MainExamFragment")
                                   .addToBackStack(null)
                                   .commit();
                       }
                       break;
                   case R.id.homeWork:
                       MainHomeWorkFragment mainHomeWork = (MainHomeWorkFragment)  getSupportFragmentManager().findFragmentByTag("MainHomeWorkFragment");
                       if(mainHomeWork!=null && mainHomeWork.isVisible()){
                           drawerLayout.closeDrawer(GravityCompat.START,true);
                       }
                       else {
                           //Toast.makeText(getApplicationContext(), "This Feature is Comming Soon", Toast.LENGTH_SHORT).show();
                           drawerLayout.closeDrawer(GravityCompat.START,true);
                           getSupportFragmentManager().popBackStack();
                           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MainHomeWorkFragment(), "MainHomeWorkFragment")
                                   .addToBackStack(null)
                                   .commit();
                       }
                       break;
                   default:
                       Toast.makeText(getApplicationContext(), "You Selected Navigation Item", Toast.LENGTH_SHORT).show();
                      break;
               }
               return false;
           }
       });
       toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, open, close);
       toggle.syncState();
      getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MainFargment(MainActivity.this),"MainFragment").commit();
    }

    @Override
    public void goToTimeTable(String dayName) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new TimeTableFragment(dayName),"TimeTableFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToToDoList(String dayName) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ToDoFragment(dayName),"ToDoFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void noteFragmentChanger() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,new NoteAddFragment(MainActivity.this,getApplicationContext()),"NoteAddFragment")
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onBackButton() {
     onBackPressed();
    }

    @Override
    public void noteShow(NoteData note) {
        NoteAddFragment noteAddFragment = new NoteAddFragment(MainActivity.this,getApplicationContext());
         Bundle bundle = new Bundle();
         bundle.putString("title",note.getTitle());
         bundle.putString("description",note.getDescription());
         bundle.putInt("id",note.getId());
        noteAddFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer,noteAddFragment,"NoteShowFragment")
                .addToBackStack(null).commit();
    }
}

