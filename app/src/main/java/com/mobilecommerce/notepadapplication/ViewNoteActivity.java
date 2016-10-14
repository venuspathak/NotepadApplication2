package com.mobilecommerce.notepadapplication;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        createAndAddAFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view,menu);
        return true;
    }

    private void createAndAddAFragment() {

        Intent intent = getIntent();// this is used to grab intent and fragment so that it launches
        // from our main activity list fragment
        MainActivity.FragmentToLoad fragmentToLoad = (MainActivity.FragmentToLoad)
                intent.getSerializableExtra(MainActivity.Second_Note_Fragment_To_Load);

        //grabs our fragment manager and fragment transaction so that we can add the edit or view fragment dynamically.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ViewNoteFragment viewNoteFragment = new ViewNoteFragment();
                setTitle(R.string.view_Note_Fragment_Title);
                fragmentTransaction.add
                        (R.id.activity_view_note, viewNoteFragment, "VIEW_NOTE_FRAGMENT");

        fragmentTransaction.commit();// using commit here to make sure that everything we did above actually happens.
    }
}
