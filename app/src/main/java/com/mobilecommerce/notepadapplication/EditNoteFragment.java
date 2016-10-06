package com.mobilecommerce.notepadapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment {

    private ImageButton noteCategoryButton;
    private Note.Category savedNoteCategoryButton;
    public AlertDialog categoryAlertDialogObject;


    public EditNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //inflate our fragment edit layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_edit_note, container, false);

        //grabbing refrences from the layout
        EditText title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        EditText body = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCategoryButton = (ImageButton) fragmentLayout.findViewById(R.id.editNoteButtonImage);

        //populating with data. We are using this to actually populate the fragment with our existing note data.
        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.Second_Note_Title));
        body.setText(intent.getExtras().getString(MainActivity.Second_Note_Body));

        Note.Category noteCategory = (Note.Category) intent.getSerializableExtra(MainActivity.Second_Note_Category);
        noteCategoryButton.setImageResource(Note.categoryToDrawble(noteCategory));

        buildCategoryDialog();

        //setting a listerner on the note category button
        noteCategoryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                categoryAlertDialogObject.show();
            }
        });

        return fragmentLayout;
    }

    private void buildCategoryDialog() {

        //defining the set of options we will show in the menu
        final String[] categories = new String[]{"Bill", "Family", "Food", "Default", "Party", "Personal", "School", "Shopping", "Thoughts"};

        //we will create an alert dialog window
        AlertDialog.Builder noteCategoryBuilder = new AlertDialog.Builder(getActivity());
        noteCategoryBuilder.setTitle("Please choose Note Category");

        noteCategoryBuilder.setSingleChoiceItems(categories, 3, new DialogInterface.OnClickListener() { // We have given 3 because we want our Default to be 'Default' which is 4th in our category array
            @Override
            public void onClick(DialogInterface dialog, int selectCategory) {

                categoryAlertDialogObject.cancel();// we will use this to dismiss our category dialog window when someone clicks and update the screen

                switch (selectCategory) {
                    case 0:

                        savedNoteCategoryButton = Note.Category.BILL;
                        noteCategoryButton.setImageResource(R.drawable.bill);

                        break;

                    case 1:
                        savedNoteCategoryButton = Note.Category.FAMILY;
                        noteCategoryButton.setImageResource(R.drawable.family);

                        break;

                    case 2:

                        savedNoteCategoryButton = Note.Category.FOOD;
                        noteCategoryButton.setImageResource(R.drawable.food);

                        break;

                    case 3:

                        savedNoteCategoryButton = Note.Category.DEFAULT;
                        noteCategoryButton.setImageResource(R.drawable.main);

                        break;

                    case 4:

                        savedNoteCategoryButton = Note.Category.PARTY;
                        noteCategoryButton.setImageResource(R.drawable.party);

                        break;

                    case 5:

                        savedNoteCategoryButton = Note.Category.PERSONAL;
                        noteCategoryButton.setImageResource(R.drawable.personal);

                        break;

                    case 6:

                        savedNoteCategoryButton = Note.Category.SCHOOL;
                        noteCategoryButton.setImageResource(R.drawable.school);

                        break;

                    case 7:

                        savedNoteCategoryButton = Note.Category.SHOPPING;
                        noteCategoryButton.setImageResource(R.drawable.shopping);

                        break;

                    case 8:

                        savedNoteCategoryButton = Note.Category.THOUGHTS;
                        noteCategoryButton.setImageResource(R.drawable.thoughts);

                        break;
                }
            }

        });
        categoryAlertDialogObject = noteCategoryBuilder.create();// we are using this to create our alert dialog window the way we want it using switch case.

    }

}
