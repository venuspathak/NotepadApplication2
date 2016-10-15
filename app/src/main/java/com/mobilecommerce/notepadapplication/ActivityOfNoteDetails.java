package com.mobilecommerce.notepadapplication;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;

public class ActivityOfNoteDetails extends AppCompatActivity {

    public static final String NEW_NOTE = "New Note";
    private ArrayList<Note> notes;
    private AdapterForNote adapterForNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_of_note_details);

        createAndAddAFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit,menu);
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

        // using switch so that we can choose the correct fragment(either edit or load) so that we load it
        switch (fragmentToLoad) {

            case ADD:
                //This is launched when a new note needs to be added, we will still use EditNoteFragment
                EditNoteFragment addNewNoteFragment = new EditNoteFragment();
                setTitle(R.string.add_Note_Fragment_Title);

                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_NOTE, true);
                addNewNoteFragment.setArguments(bundle);

                fragmentTransaction.add
                        (R.id.activity_of_note_details,addNewNoteFragment, "ADD_NOTE_FRAGMENT");
                break;

            case EDIT:
                EditNoteFragment editNoteFragment = new EditNoteFragment();
                setTitle(R.string.edit_Note_Fragment_Title);
                fragmentTransaction.add
                        (R.id.activity_of_note_details,editNoteFragment, "EDIT_NOTE_FRAGMENT");
                break;

            case VIEW:
                //create and add note view fragment to edit note activity if we want it to launch
                ViewNoteFragment viewNoteFragment = new ViewNoteFragment();
                setTitle(R.string.view_Note_Fragment_Title);
                fragmentTransaction.add
                        (R.id.activity_of_note_details, viewNoteFragment, "VIEW_NOTE_FRAGMENT");
                break;
        }

        fragmentTransaction.commit();// using commit here to make sure that everything we did above actually happens.
    }
}
