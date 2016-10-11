package com.mobilecommerce.notepadapplication;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.widget.AdapterView;
            import android.widget.ArrayAdapter;
            import android.widget.ListView;
            import android.widget.Toast;

            import java.io.BufferedReader;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.util.ArrayList;



    public class MainActivityListFragment extends ListFragment {

        private ArrayList<Note> notes;
        private AdapterForNote adapterForNote;
        private static final String noteTextFile = "noteTextFile10.txt";
        private String[] rowsOfNotes;
        private String[][] entireNote = new String[10][];;


        public void onActivityCreated(Bundle savedInstance) {
            super.onActivityCreated(savedInstance);



        notes = new ArrayList<Note>();
        getTextFromNoteFile(notes);

        /*
        notes.add(new Note("PERSONAL NOTE", "This is my personal note", Note.Category.PERSONAL));
        notes.add(new Note("BILL", "Phone bill for September", Note.Category.BILL));
        notes.add(new Note("FAMILY RELATED NOTE", "Family gathering this week", Note.Category.FAMILY));
        notes.add(new Note("FOOD EXPENDITURE", "Spent $50 on Dinner", Note.Category.FOOD));
        notes.add(new Note("PARTY THIS MONTH", "Party for new graduate students", Note.Category.PARTY));
        notes.add(new Note("BOOK RELATED EXPENSES", "Spent $200 for books", Note.Category.SCHOOL));
        notes.add(new Note("WALMART", "Grocery on Sep 28", Note.Category.SHOPPING));
        notes.add(new Note("SONG PLAYLIST", "This has playlist of all the songs I like", Note.Category.THOUGHTS));
        notes.add(new Note("USERNAME FOR MY HOMEPAGE", "My username and password", Note.Category.DEFAULT));
        notes.add(new Note("TEST NOTE", "I am going to write a really long note and see if the scrolling for this " +
                "test note actually works or not. Otherwise we might have to think of something else to do. Now I am " +
                "just going to copy a few lines from my favorite poem just so we have a lot of lines             " +
                " Whose woods these are I think I know.His house is in the village though;He will not see me stopping" +
                " here To watch his woods fill up with snow.My little horse must think it queer To stop without a farmhouse" +
                " near Between the woods and frozen lake The darkest evening of the year.His gives his harness bells a " +
                "shake To ask if there is some mistake. The only other sound's the sweep of easy wind and downy flake. " +
                "The woods are lovely,dark and deep, but I have promises to keep and miles to go before I sleep and miles " +
                "to go before I sleep", Note.Category.THOUGHTS));

*/
        adapterForNote = new AdapterForNote(getActivity(), notes);
        setListAdapter(adapterForNote);

            getListView().setDivider(ContextCompat.getDrawable(getActivity(),android.R.color.darker_gray));
            getListView().setDividerHeight(1);

            registerForContextMenu(getListView());

    }
        @Override
        public void onListItemClick(ListView listView, View view, int position, long id) {
            super.onListItemClick(listView, view, position, id);

            /*we are using position of the click to launchNoteDetailActivity such that we get the information
            based on where we click on the screen */

            launchNoteDetailActivity(MainActivity.FragmentToLoad.VIEW, position);
        }

        private void launchNoteDetailActivity(MainActivity.FragmentToLoad fragmentToLoad, int position) // launchNoteDetailActivity will show us individual notes.
        {
            /*We are using this to basically grab all the data related to the note we have clicked.
              We will create a new intent and further pass along the information of a particular
              note such as its ID,Category,Title and Body */

            Note note = (Note) getListAdapter().getItem(position);

            Intent intent = new Intent(getActivity(),ActivityOfNoteDetails.class);
            intent.putExtra(MainActivity.Second_Note_Id, note.getNoteId());
            intent.putExtra(MainActivity.Second_Note_Category,note.getCategory());
            intent.putExtra(MainActivity.Second_Note_Title, note.getTitle());
            intent.putExtra(MainActivity.Second_Note_Body,note.getDescription());

            switch (fragmentToLoad){

                case VIEW:

                    intent.putExtra(MainActivity.Second_Note_Fragment_To_Load, MainActivity.FragmentToLoad.VIEW);
                    break;

                case EDIT:
                    intent.putExtra(MainActivity.Second_Note_Fragment_To_Load, MainActivity.FragmentToLoad.EDIT);
                    break;
            }

            startActivity(intent);
        }

        public void getTextFromNoteFile(ArrayList notes){
            final Context context = getActivity().getApplicationContext();
            try {
                File file = context.getFileStreamPath(noteTextFile);
                if(file.exists()) {
                    InputStream inputStream = context.openFileInput(noteTextFile);

                    if (inputStream != null) {

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String string;
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((string = bufferedReader.readLine()) != null) {
                            stringBuilder.append(string + "\n");
                        }

                        inputStream.close();
                        Log.d("OUTPUT", stringBuilder.toString());
                        String fileText = stringBuilder.toString();
                        rowsOfNotes = fileText.split("\n");

                        Log.d("OUTPUT ROWS", rowsOfNotes[0]+","+rowsOfNotes[1]+","+rowsOfNotes[2]);

                        Log.d("LENGTH", String.valueOf(rowsOfNotes.length));


                        for(int rowNumber=0; rowNumber< rowsOfNotes.length; rowNumber++) {
                            entireNote[rowNumber] = rowsOfNotes[rowNumber].split(",");
                            Log.d("NOTE LENGTH",String.valueOf(entireNote[rowNumber].length));
                            Log.d("NOTE TILE",entireNote[rowNumber][0] );
                        }
                        for(int noteParts=0; noteParts<rowsOfNotes.length; noteParts++) {
                            Note.Category category=null;

                            if(entireNote[noteParts][2].equals("PERSONAL"))
                                category = Note.Category.PERSONAL;
                            else if(entireNote[noteParts][2].equals("FAMILY"))
                                category = Note.Category.FAMILY;
                            else if(entireNote[noteParts][2].equals("SCHOOL"))
                                category = Note.Category.SCHOOL;
                            else if(entireNote[noteParts][2].equals("BILL"))
                                category = Note.Category.BILL;
                            else if(entireNote[noteParts][2].equals("FOOD"))
                                category = Note.Category.FOOD;
                            else if(entireNote[noteParts][2].equals("DEFAULT"))
                                category = Note.Category.DEFAULT;
                            else if(entireNote[noteParts][2].equals("PARTY"))
                                category = Note.Category.PARTY;
                            else if(entireNote[noteParts][2].equals("SHOPPING"))
                                category = Note.Category.SHOPPING;
                            else if(entireNote[noteParts][2].equals("THOUGHTS"))
                                category = Note.Category.THOUGHTS;

                            //Log.d("LENGTH OF ARRAY", String.valueOf(rowsOfNotes.length));
                            //Log.d("TITLE: " + (noteParts + 1), entireNote[noteParts][0]);
                            //Log.d("BODY: " + (noteParts + 1), entireNote[noteParts][1]);
                            //Log.d("CATEGORY: " + (noteParts + 1), entireNote[noteParts][2]);

                            notes.add(new Note(entireNote[noteParts][0],entireNote[noteParts][1],category));
                        }

                    }

                }

            }catch(FileNotFoundException e){
                Log.d("EXCEPTION: ", e.toString());
            }

            catch(Throwable throwable){
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onCreateContextMenu (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            super.onCreateContextMenu(contextMenu,view,contextMenuInfo);

            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_on_long_press, contextMenu);

        }

        @Override
        public boolean onContextItemSelected(MenuItem menuItem){//it returns the if of whatever menu we select

            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
            int rowPosition = adapterContextMenuInfo.position;

            switch (menuItem.getItemId()) {
                case R.id.edit:// if we press edit, it will return the following

                    launchNoteDetailActivity(MainActivity.FragmentToLoad.EDIT, rowPosition);
                    return true;
            }
            return super.onContextItemSelected(menuItem);
        }
}