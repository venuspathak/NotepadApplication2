package com.mobilecommerce.notepadapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by User 1 on 06/10/2016.
 */

public class MyDatabaseHandler extends SQLiteOpenHelper {

    private static final int databaseVersion = 1;
    private static final String databaseName = "databaseForNote.db";
    private static final String tableForNote = "notes";

    //public static final String columnID = "id";
    public static final String columnTitle = "title";
    public static final String columnContent = "content";
    public static final String columnImage = "image";

    public MyDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        // Constructor made for calling the constructor of super class SQLiteOpenHelper
        super(context,databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
       // String CREATE_NOTE_TABLE_QUERY = "CREATE TABLE"+tableForNote+"("+columnID+"INTEGER PRIMARY KEY,"+columnTitle+"TEXT"+columnContent+"TEXT"+columnImage+"TEXT"+")";

        String CREATE_NOTE_TABLE_QUERY = "CREATE TABLE"+tableForNote+"("+columnTitle+"TEXT PRIMARY KEY"+columnContent+"TEXT"+columnImage+"TEXT"+")";
        database.execSQL(CREATE_NOTE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS"+tableForNote);
        onCreate(database);
    }

    public void addNotes(NoteDatabase note)
    {
        ContentValues values = new ContentValues();
        //values.put(columnID, note.getId());
        values.put(columnTitle, note.getTitle());
        values.put(columnContent, note.getContent());

        try {
            FileInputStream fileInputStream = new FileInputStream("@drawable/bill");
            byte [] image = new byte[fileInputStream.available()];
            fileInputStream.read(image);
            values.put(columnImage, image);

            SQLiteDatabase database = this.getWritableDatabase();
            database.insert(tableForNote, null, values);
            database.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

    public boolean deleteNote(String noteID){
        boolean result = false;

        String query = "Select * FROM "+tableForNote;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        NoteDatabase note = new NoteDatabase();

        if(cursor.moveToNext()){
            note.setTitle(cursor.getString(0));
           // database.delete(tableForNote,columnID+" =?", new String[] {String.valueOf(note.getId())});
            database.delete(tableForNote,columnTitle+" =?", new String[] {note.getTitle()});
            cursor.close();
            result=true;
        }
        database.close();
        return result;
    }

}
