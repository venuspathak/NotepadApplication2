package com.mobilecommerce.notepadapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity{

    //Using the package name so that we donot get confused between keys when we will try to implement the share on facebook part
    public static final String Second_Note_Id = "com.mobilecommerce.notepadapplication.Note Identifier";
    public static final String Second_Note_Title = "com.mobilecommerce.notepadapplication.Note Title";
    public static final String Second_Note_Body = "com.mobilecommerce.notepadapplication.Note Body";
    public static final String Second_Note_Category = "com.mobilecommerce.notepadapplication.Note Category";
    public static final String Second_Note_Fragment_To_Load = "com.mobilecommerce.notepadapplication.Fragment To Load";
    public enum FragmentToLoad{VIEW, EDIT, ADD}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_add_note){
            Intent intentNoteDetail = new Intent(this, ActivityOfNoteDetails.class);
            intentNoteDetail.putExtra(MainActivity.Second_Note_Fragment_To_Load, FragmentToLoad.ADD);
            startActivity(intentNoteDetail);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
