package com.mobilecommerce.notepadapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {


    public void onActivityCreated(Bundle savedInstance)
    {
      super.onActivityCreated(savedInstance);

        String[] notes = new String[] {"My first note", "I owe $100 to Shawn", "Family Gathering on Oct 12", "Remind Mom about Party"};
        ArrayAdapter<String> notesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, notes);

        setListAdapter(notesAdapter);

     }

    public void onListItemClick(ListView listView, View view, int position, long id)
    {
      super.onListItemClick(listView, view, position, id);
    }
}
