package com.mobilecommerce.notepadapplication;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityOfNoteDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_of_note_details);

        createAndAddAFragment();
    }

    private void createAndAddAFragment() {

        Intent intent = getIntent();// this is used to grab intent and fragment so that it launches
        // from our main activity list fragment
        MainActivity.FragmentToLoad fragmentToLoad = (MainActivity.FragmentToLoad)
                intent.getSerializableExtra(MainActivity.Second_Note_Fragment_To_Load);

        //grabs our fragment manager and fragment transaction so that we can add the edit or view fragment dynamically.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // using switch so that we can choose the correct fragment(either edit or load) so that we oad it
        switch (fragmentToLoad) {
            case EDIT:

                //create and add note edit fragment to edit note activity if we want it to launch
                EditNoteFragment editNoteFragment = new EditNoteFragment();
                setTitle(R.string.edit_Note_Fragment_Title);
                fragmentTransaction.add
                        (R.id.activity_of_note_details,editNoteFragment, "Edit Note");

                break;

            case VIEW:

                //create and add note edit fragment to edit note activity if we want it to launch
                ViewNoteFragment viewNoteFragment = new ViewNoteFragment();
                setTitle("View Note");
                fragmentTransaction.add(R.id.activity_of_note_details, viewNoteFragment, "View Note");

                break;
        }

        fragmentTransaction.commit();// using commit here to make sure that everything we did above actually happens.
    }
}
