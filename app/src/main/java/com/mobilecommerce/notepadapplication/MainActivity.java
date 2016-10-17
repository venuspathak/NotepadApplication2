package com.mobilecommerce.notepadapplication;


import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MainActivity extends AppCompatActivity{

    //Using the package name so that we donot get confused between keys when we will try to implement the share on facebook part
    public static final String Second_Note_Id = "com.mobilecommerce.notepadapplication.Note Identifier";
    public static final String Second_Note_Title = "com.mobilecommerce.notepadapplication.Note Title";
    public static final String Second_Note_Body = "com.mobilecommerce.notepadapplication.Note Body";
    public static final String Second_Note_Category = "com.mobilecommerce.notepadapplication.Note Category";
    public static final String Second_Note_Fragment_To_Load = "com.mobilecommerce.notepadapplication.Fragment To Load";
    public static final String Second_Note_Color_Category = "com.mobilecommerce.notepadapplication.Color Category";
    public enum FragmentToLoad{VIEW, EDIT, ADD}
    private FloatingActionButton addNewNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        addNewNote = (FloatingActionButton) findViewById(R.id.action_add_note);
        addNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNote();
            }
        });

    }

    public void AddNewNote(){
        Intent intentNoteDetail = new Intent(this, ActivityOfNoteDetails.class);
        intentNoteDetail.putExtra(MainActivity.Second_Note_Fragment_To_Load, FragmentToLoad.ADD);
        startActivity(intentNoteDetail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the emnu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Handle all items here
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        Toast.makeText(this, "Orientation Changed", Toast.LENGTH_LONG).show();
        super.onConfigurationChanged(newConfiguration);
    }


}
