/*
Authors: Venus Pathak - 7972526
         Shivjot Baidwan - 8028412
 */
package com.mobilecommerce.notepadapplication;

import android.content.Intent;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class ViewNoteFragment extends Fragment {


    public ViewNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutFragment = inflater.inflate(R.layout.fragment_view_note, container, false);

        TextView title = (TextView) layoutFragment.findViewById(R.id.viewNoteTitle);
        TextView body = (TextView) layoutFragment.findViewById(R.id.viewNoteBody);
        ImageView imageIcon = (ImageView) layoutFragment.findViewById(R.id.noteImage);

        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.Second_Note_Title));
        body.setText(intent.getExtras().getString(MainActivity.Second_Note_Body));
        Note.Category noteCategory = (Note.Category) intent.getSerializableExtra(MainActivity.Second_Note_Category);// we have used getSerializable here instead of getString as data type of Category is enum.
        imageIcon.setImageResource(Note.categoryToDrawble(noteCategory));
   /*

        if(noteBoldToBeUsedByAllInEdit.equals("true")){
            title.setTypeface(title.getTypeface(), Typeface.BOLD);
            body.setTypeface(body.getTypeface(), Typeface.BOLD);
        }

        if(noteItalicsToBeUsedByAllInEdit.equals("true")){
            title.setTypeface(title.getTypeface(), Typeface.ITALIC);
            body.setTypeface(body.getTypeface(), Typeface.ITALIC);
        }

        if(noteUnderlineToBeUsedByAllInEdit.equals("true")){
            title.setPaintFlags(title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            body.setPaintFlags(body.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        }
*/
        return layoutFragment;
    }

}
