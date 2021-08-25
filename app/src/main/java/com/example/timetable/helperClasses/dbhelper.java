package com.example.timetable.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class dbhelper  {
    private final String dbName ="TimeTableDataBase";
    private final String TTableTName="timeTable";
    private final String ToDoTableName="toDoTable";
    private final String TTWork ="timeTableWork";
    private final String toDoWork ="toDoWork";
    private final String fromTime="fromTime";
    private final String toTime ="toTime";
    private final String ttDayname ="dayname";
    private final String tdDayname ="dayname";
    private final String noteTableName="noteTable";
    private final String titleColumn="noteTitle";
    private final String descriptionColumn = "noteDescription";
    private final String examTableName = "examTable";
    private final String examName ="examName";
    private final String examPlace ="examPlace";
    private  final String examDate ="examDate";
    private final String teacherTableName="teacherTable";
    private  final String teacherName ="name";
    private final String teacherPhone ="phoneNo";
    private final String  teacherSub ="subject";
    private final String homeWorkSub = "homeworksubject";
    private final String homeWorkdes = "homeworkdescription";
    private final String homeWorkdate ="completionDate";
    private final String homeWorkTable ="homeworktable";
    int dbversion;
    private String idColumn ="id";
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    Context context;
    public dbhelper(Context context){
        this.context = context;
    }



    class DataBaseHelper extends SQLiteOpenHelper{

        public DataBaseHelper(@Nullable Context context) {
            super(context,dbName , null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TTableTName+"("+ idColumn +"" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TTWork+" TEXT,"+
                    fromTime+" TEXT ,"+
                    toTime+" TEXT ,"+
                    ttDayname+" TEXT);");


            db.execSQL("CREATE TABLE "+ToDoTableName+"("+ idColumn +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+toDoWork+" TEXT,"+tdDayname+" TEXT);");
            db.execSQL("CREATE TABLE "+noteTableName+"("+idColumn+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                         titleColumn+" TEXT,"+descriptionColumn+" TEXT);");
            db.execSQL("CREATE TABLE "+examTableName+"("+idColumn+" INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + examName+" TEXT ,"+examDate+" TEXT, "+examPlace+" TEXT );" );
            db.execSQL("CREATE TABLE "+teacherTableName+"("+idColumn +" INTEGER PRIMARY KEY AUTOINCREMENT, "+teacherName+" TEXT, "+teacherSub
            +" TEXT,"+teacherPhone+" TEXT);");
            db.execSQL("CREATE TABLE "+homeWorkTable+"("+idColumn+" INTEGER PRIMARY KEY AUTOINCREMENT, "+homeWorkdes+" TEXT, "+
                    homeWorkdate+" TEXT,  "+homeWorkSub+" TEXT);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          switch (oldVersion){
              case 1:
                  db.execSQL("DROP TABLE IF EXISTS "+TTableTName);
              case 2:
                  db.execSQL("DROP TABLE IF EXISTS "+ToDoTableName);
              case 3:
                  db.execSQL("DROP TABLE IF EXISTS "+noteTableName);
              case 4:
                  db.execSQL("DROP TABLE IF EXISTS "+examTableName);
              case 5:
                  db.execSQL("DROP TABLE IF EXISTS "+teacherTableName);
                  break;

          }
          onCreate(db);
        }
    }
    public void insertTTWork(TimeTableData ttd){
        ContentValues contentValues= new ContentValues();
        contentValues.put(TTWork,ttd.getWork());
        contentValues.put(ttDayname,ttd.getDayName());
        contentValues.put(fromTime,ttd.getFromTime());
        contentValues.put(toTime,ttd.getToTime());
        db.insert(TTableTName,null,contentValues);
    }
    public void getConnection(){
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }
   public ArrayList<TimeTableData> getTimeTableData(String dayName){
       Cursor cursor = db.query(TTableTName,new String[]{TTWork,ttDayname,toTime,fromTime, idColumn},ttDayname+"=?",new String[]{dayName},null,null,null);
       int workColumn = cursor.getColumnIndex(TTWork);
       int idColumn = cursor.getColumnIndex(this.idColumn);
       int fromtimecolumn = cursor.getColumnIndex(fromTime);
       int toTimeColumn = cursor.getColumnIndex(toTime);
       int daynamecolumn = cursor.getColumnIndex(ttDayname);
       ArrayList<TimeTableData> datalist= new ArrayList<>();
       for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
           TimeTableData data = new TimeTableData();
           data.setWork(cursor.getString(workColumn));
           data.setDayName(cursor.getString(daynamecolumn));
           data.setId(Integer.parseInt(cursor.getString(idColumn)));
           data.setFromTime(cursor.getString(fromtimecolumn));
           data.setToTime(cursor.getString(toTimeColumn));
           datalist.add(data);
       }
       return datalist;
   }
   public void deleteTTItem(int Id){
        db.delete(TTableTName, idColumn +"=?",new String[]{Id+""});
   }
   public void closeConnection(){
        db.close();
   }
   public void update(TimeTableData data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TTWork,data.getWork());
        contentValues.put(fromTime,data.getFromTime());
        contentValues.put(toTime,data.getToTime());
        db.update(TTableTName,contentValues, idColumn +"=?",new String[]{data.getId()+""});
   }
   public void insertIntoToDo(ToDoData data){                       //For Inserting The Data Into To To_Do Table
        ContentValues contentValues = new ContentValues();
        contentValues.put(toDoWork,data.getWork());
        contentValues.put(tdDayname,data.getDayname());
        db.insert(ToDoTableName,null,contentValues);
   }
   public ArrayList<ToDoData> getToDoData(String dayName) throws SQLiteException {           //For Getting The Data From The To Do Table
    ArrayList<ToDoData> toDoData = new ArrayList<>();
       Cursor cursor;
    try {
        cursor = db.query(ToDoTableName, new String[]{idColumn, toDoWork, tdDayname}, tdDayname+"=?",new String[]{dayName}, null, null, null);
     int workIndex = cursor.getColumnIndex(toDoWork);
    int idIndex = cursor.getColumnIndex(idColumn);
    int dayindex = cursor.getColumnIndex(tdDayname);
    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
        ToDoData data = new ToDoData();
        data.setId(cursor.getInt(idIndex));
        data.setDayname(cursor.getString(dayindex));
        data.setWork(cursor.getString(workIndex));
        toDoData.add(data);
    }}catch (SQLiteException e){
        Toast.makeText(context, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    return  toDoData;
    }
    public  void deleteToDo(int id ){
        db.delete(ToDoTableName,idColumn+"=?",new String[]{id+""});
        Log.d("Delete To Do ", "deleteToDo: Is Running ");
    }
   // public void updateToDo(){}
    //Note Methods
    public void addNote(NoteData notedata){
        ContentValues contentValues = new ContentValues();
        contentValues.put(titleColumn,notedata.getTitle());
        contentValues.put(descriptionColumn, notedata.getDescription());
        db.insert(noteTableName,null,contentValues);
    }
    public ArrayList<NoteData> getNoteData() throws SQLiteException{
        ArrayList<NoteData> noteList = new ArrayList<>();
        Cursor cursor = db.query(noteTableName,new String[]{titleColumn,idColumn,descriptionColumn},null,null,null,null,null);
        int titleindex = cursor.getColumnIndex(titleColumn);
        int descriptionIndex = cursor.getColumnIndex(descriptionColumn);
        int idIndex = cursor.getColumnIndex(idColumn);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            NoteData note = new NoteData();
            note.setId(cursor.getInt(idIndex));
            note.setTitle(cursor.getString(titleindex));
            note.setDescription(cursor.getString(descriptionIndex));
            noteList.add(note);
        }
        return  noteList;
    }
    public void deleteNote(int id) {
      db.delete(noteTableName,idColumn+"=?",new String[]{id+""});
    }
    public void updateNote(NoteData note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(titleColumn,note.getTitle());
        contentValues.put(descriptionColumn,note.getDescription());
        db.update(noteTableName,contentValues,idColumn+"=?",new String[]{note.getId()+""});
    }
    //TODO: Handle Exam Methods
    public void addExam(ExamData examData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(examName, examData.getExamName());
        contentValues.put(examDate ,examData.getExamDate());
        contentValues.put(examPlace,examData.getExamPlace());
        db.insert(examTableName,null,contentValues);
    }
    public ArrayList<ExamData> getExamData() {
        ArrayList<ExamData> examList = new ArrayList<>();
        try{
        Cursor cursor = db.query(examTableName,new String[]{examName,examDate,examPlace,idColumn},null,null,null,null,null);
        int nameIndex = cursor.getColumnIndex(examName);
        int dateIndex = cursor.getColumnIndex(examDate);
        int placeIndex = cursor.getColumnIndex(examPlace);
        int idIndex = cursor.getColumnIndex(idColumn);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            ExamData data = new ExamData();
            data.setExamName(cursor.getString(nameIndex));
            data.setId(cursor.getInt(idIndex));
            data.setExamPlace(cursor.getString(placeIndex));
            data.setExamDate(cursor.getString(dateIndex));
            examList.add(data);
        }}
        catch (Exception e){
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
        }
        return  examList;
    }
    public void deleteExam(int id ){
        db.delete(examTableName,idColumn+"=?",new String[]{id+""});
    }
    public void updateExam(ExamData examData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(examName, examData.getExamName());
        contentValues.put(examDate ,examData.getExamDate());
        contentValues.put(examPlace,examData.getExamPlace());
        db.update(examTableName,contentValues,idColumn+"=?",new String[]{examData.getId()+""});
    }

    public void addTeacher(TeacherData teacherData){
        ContentValues contentValues= new ContentValues();
        contentValues.put(teacherName,teacherData.getName());
        contentValues.put(teacherPhone,teacherData.getPhoneNumber());
        contentValues.put(teacherSub,teacherData.getSubject());
        db.insert(teacherTableName,null,contentValues);

    }

    public void deleteTeacher(int id ){
        db.delete(teacherTableName,idColumn+"=?",new String[]{id+""});
    }

    public void updateTeacher(TeacherData teacherData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(teacherName,teacherData.getName());
        contentValues.put(teacherSub,teacherData.getSubject());
        contentValues.put(teacherPhone,teacherData.getPhoneNumber());
        db.update(teacherTableName,contentValues,idColumn+"=?",new String[]{teacherData.getId()+""});
    }

    public ArrayList<TeacherData> getTeachers(){
        ArrayList<TeacherData> teacherList = new ArrayList<>();
        Cursor cursor=  db.query(teacherTableName,new String[]{teacherName,teacherPhone,teacherSub,idColumn},null,null,null,null,null);
        int idIndex = cursor.getColumnIndex(idColumn);
        int nameIndex = cursor.getColumnIndex(teacherName);
        int subIndex = cursor.getColumnIndex(teacherSub);
        int phoneIndex = cursor.getColumnIndex(teacherPhone);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            TeacherData teacherData = new TeacherData();
            teacherData.setName(cursor.getString(nameIndex));
            teacherData.setPhoneNumber(cursor.getString(phoneIndex));
            teacherData.setSubject(cursor.getString(subIndex));
            teacherData.setId(cursor.getInt(idIndex));
            teacherList.add(teacherData);
        }
        return teacherList;
    }
    public void addHomeWork(HomeWorkData data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(homeWorkSub,data.getSubject());
        contentValues.put(homeWorkdes,data.getDescription());
        contentValues.put(homeWorkdate,data.getCompletionDate());
        db.insert(homeWorkTable,null,contentValues);
    }
    public void deleteHomeWork(int id ){
        db.delete(homeWorkTable,idColumn+"=?",new String[]{id+""});
    }
    public void updateHomeWork(HomeWorkData data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(homeWorkSub,data.getSubject());
        contentValues.put(homeWorkdes,data.getDescription());
        contentValues.put(homeWorkdate,data.getCompletionDate());
        db.update(homeWorkTable,contentValues,idColumn+"=?",new String[]{data.getId()+""});
    }
    public ArrayList<HomeWorkData> getHomeWorkData(){
        ArrayList<HomeWorkData> datalist = new ArrayList<>();
        Cursor cursor = db.query(homeWorkTable,new String[]{homeWorkdate,homeWorkSub,homeWorkdes,idColumn},null,null,null,null,null);
        int subIndex = cursor.getColumnIndex(homeWorkSub);
        int desIndex =cursor.getColumnIndex(homeWorkdes);
        int dateIndex = cursor.getColumnIndex(homeWorkdate);
        int idIndex = cursor.getColumnIndex(idColumn);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            HomeWorkData data = new HomeWorkData();
            data.setSubject(cursor.getString(subIndex));
            data.setDescription(cursor.getString(desIndex));
            data.setCompletionDate(cursor.getString(dateIndex));
            data.setId(cursor.getInt(idIndex));
            datalist.add(data);
        }
        return datalist;
    }
}
