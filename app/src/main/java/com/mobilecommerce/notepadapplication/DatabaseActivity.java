package com.mobilecommerce.notepadapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by User 1 on 07/10/2016.
 */

public class DatabaseActivity extends AppCompatActivity {

   // TextView id;
    TextView noteTitle;
    TextView noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

       /* setContentView(R.layout.fragment_edit_note);

        Button saveButton = (Button)findViewById(R.id.saveNoteButton);
       // saveButton.callOnClick(saveNote(R.layout.fragment_edit_note));

        noteTitle = (TextView) findViewById(R.id.editNoteTitle);
        noteContent = (TextView) findViewById(R.id.editNoteMessage);
        */
    }

    public void saveNote (View view){
        MyDatabaseHandler myDatabaseHandler = new MyDatabaseHandler(this,null,null,1);

        NoteDatabase note = new NoteDatabase(MainActivity.Second_Note_Title, MainActivity.Second_Note_Body);
        myDatabaseHandler.addNotes(note);
    }
}
