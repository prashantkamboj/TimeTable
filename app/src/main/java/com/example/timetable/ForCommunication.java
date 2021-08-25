package com.example.timetable;

import com.example.timetable.helperClasses.NoteData;

public interface ForCommunication {
    void goToTimeTable(String dayName);
    void goToToDoList(String dayName);
    void noteFragmentChanger();
    void onBackButton();
    void noteShow(NoteData note);
}