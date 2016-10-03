    package com.mobilecommerce.notepadapplication;

            import android.app.ListFragment;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.content.ContextCompat;
            import android.view.View;
            import android.widget.ArrayAdapter;
            import android.widget.ListView;

            import java.util.ArrayList;

    public class MainActivityListFragment extends ListFragment {

        private ArrayList<Note> notes;
        private AdapterForNote adapterForNote;

        public void onActivityCreated(Bundle savedInstance) {
            super.onActivityCreated(savedInstance);

/*
        String[] notes = new String[] {"My first note", "I owe $100 to Shawn", "Family Gathering on Oct 12", "Remind Mom about Party"};
        ArrayAdapter<String> notesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, notes);

        setListAdapter(notesAdapter);
*/

        notes = new ArrayList<Note>();
        notes.add(new Note("PERSONAL NOTE", "This is my personal note", Note.Category.PERSONAL));
        notes.add(new Note("BILL", "Phone bill for September", Note.Category.BILL));
        notes.add(new Note("FAMILY RELATED NOTE", "Family gathering this week", Note.Category.FAMILY));
        notes.add(new Note("FOOD EXPENDITURE", "Spent $50 on Dinner", Note.Category.FOOD));
        notes.add(new Note("PARTY THIS MONTH", "Party for new graduate students", Note.Category.PARTY));
        notes.add(new Note("BOOK RELATED EXPENSES", "Spent $200 for books", Note.Category.SCHOOL));
        notes.add(new Note("WALMART", "Grocery on Sep 28", Note.Category.SHOPPING));
        notes.add(new Note("SONG PLAYLIST", "This has playlist of all the songs I like", Note.Category.THOUGHTS));
        notes.add(new Note("USERNAME FOR MY HOMEPAGE", "My username and password", Note.Category.DEFAULT));


        adapterForNote = new AdapterForNote(getActivity(), notes);
        setListAdapter(adapterForNote);

            getListView().setDivider(ContextCompat.getDrawable(getActivity(),android.R.color.darker_gray));
            getListView().setDividerHeight(1);

    }
        @Override
        public void onListItemClick(ListView listView, View view, int position, long id) {
            super.onListItemClick(listView, view, position, id);

            /*we are position of the click to launchNoteDetailActivity such that we get the information
            based on where we click on the screen */

            launchNoteDetailActivity(position);
        }

        private void launchNoteDetailActivity(int position) // launchNoteDetailActivity will show us individual notes.
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

            startActivity(intent);
        }
}