/*
Authors: Venus Pathak - 7972526
         Shivjot Baidwan - 8028412
 */
package com.mobilecommerce.notepadapplication;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ViewNoteActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        createAndAddAFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_view,menu);

        menu.clear();
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_view,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share_note);

        //Getting SharedActionProvider
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
       // shareActionProvider.setShareHistoryFileName("share_history.xml");

        Intent intentForShare = new Intent(Intent.ACTION_SEND);
        intentForShare.setType("text/plain");
        intentForShare.putExtra(Intent.EXTRA_TEXT, "text to share"); // Here the note shared will be written
        setIntentForShare("text to share", intentForShare);

        return true;

    }

    private void setIntentForShare(String stringToShare, Intent intentForShare){
        if(shareActionProvider!=null) {
            shareActionProvider.setShareIntent(intentForShare);
        }
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
