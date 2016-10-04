package com.mobilecommerce.notepadapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityOfNoteDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_of_note_details);

        createAndAFragment();
    }

    private void createAndAFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ViewNoteFragment viewNoteFragment = new ViewNoteFragment();
        setTitle(R.string.view_Note_Fragment_Title);
        fragmentTransaction.add(R.id.activity_of_note_details, viewNoteFragment, "VIEW_NOTE_FRAGMENT");

        fragmentTransaction.commit();// using commit here to make sure that everything we did above actually happens.
    }
}
