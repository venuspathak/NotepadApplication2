package com.mobilecommerce.notepadapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment {

    private ImageButton noteCategoryButton, noteColorCategoryButton;
    private Note.Category savedNoteCategoryButton;
    private Colors.ColorCategory savedColorCategoryButton;
    public AlertDialog categoryAlertDialogObject, dialogForConfirm, colorAlertDialogObject;
    private EditText title, body;
    private static final String categoryModified = "Modified Category";
    public Boolean newNote = false;
    private static final String noteTextFile = "noteTextFile10.txt";
    private EditText textEditor;
    private Note.Category noteCategoryFinal;

    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1;
    private ImageView editNoteGallery, imageView, editNoteCamera;
    private Bitmap bitmapImage;
    private String currentPhotoPath;
    private static final String TAG = "";
    private RelativeLayout relativeLayout;

    Uri uri;


    public EditNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Grabs the bundle for new note
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            newNote = bundle.getBoolean(ActivityOfNoteDetails.NEW_NOTE, false);
        }

        if(savedInstanceState!=null){
            savedNoteCategoryButton= (Note.Category) savedInstanceState.get(categoryModified);
        }

        //inflate our fragment edit layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_edit_note, container, false);

        //grabbing refrences from the layout
        title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        body = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCategoryButton = (ImageButton) fragmentLayout.findViewById(R.id.editNoteButtonImage);
        Button saveButton = (Button) fragmentLayout.findViewById(R.id.saveNoteButton);
        imageView = (ImageView) fragmentLayout.findViewById(R.id.imageView);
        editNoteGallery = (ImageView)fragmentLayout.findViewById(R.id.editNoteGallery);
        noteColorCategoryButton = (ImageButton)fragmentLayout.findViewById(R.id.colorPicker);
        editNoteCamera = (ImageView) fragmentLayout.findViewById(R.id.editNoteCamera);
        relativeLayout = (RelativeLayout) fragmentLayout.findViewById(R.id.backGroundColor);

        //Setting onClick Listerner on Gallery Button
        editNoteGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        //Setting onClick Listerner on Camera Button
        editNoteCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });


        //Seting onCLick Listerner on ColorCategory Button
        noteColorCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorAlertDialogObject.show();
            }
        });





        //populating with data. We are using this to actually populate the fragment with our existing note data.
        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.Second_Note_Title, "")); // IF it can't find the keys since it is a new note, create a new note
        body.setText(intent.getExtras().getString(MainActivity.Second_Note_Body, ""));

        // Orientation has been changed if the category is grabbed from bundle

        if(savedNoteCategoryButton!=null){
            noteCategoryButton.setImageResource(Note.categoryToDrawble(savedNoteCategoryButton));
        }else if(!newNote) { // this is coming from the fragment
            Note.Category noteCategory = (Note.Category) intent.getSerializableExtra(MainActivity.Second_Note_Category);
            savedNoteCategoryButton = noteCategory;
            noteCategoryButton.setImageResource(Note.categoryToDrawble(noteCategory));
           // noteCategoryFinal=noteCategory; // This has been done to set the global variable with the modified category so that
            // it can be accessed in the method for writing into file
        }
    //    else if(newNote){
      //      Note.Category noteCategory = (Note.Category) intent.getSerializableExtra(MainActivity.);
        //    savedNoteCategoryButton = noteCategory;
          //  noteCategoryButton.setImageResource(Note.categoryToDrawble(noteCategory));
        //}

        buildCategoryDialog();
        buildingConfirmDialog(fragmentLayout);
        buildColorPickerDialog();


        //setting a listener on the note category button
        noteCategoryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                categoryAlertDialogObject.show();
            }
        });


        //setting a listener on the save button
        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialogForConfirm.show();
                final String changedTitle = title.getText().toString();
                final String changedBody = body.getText().toString();

                title.setText(changedTitle, TextView.BufferType.EDITABLE);
                body.setText(changedBody, TextView.BufferType.EDITABLE);
            }
        });

        return fragmentLayout;
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }
    private void openCamera(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException exception) {
                Log.i(TAG, "IOException");
            }
            if(photoFile != null) {
                camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(camera, CAMERA_REQUEST);
            }
        }

    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" +timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDirectory
        );

        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(categoryModified, savedNoteCategoryButton);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try{
                bitmapImage = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), Uri.parse(currentPhotoPath));
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
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

    public void buildingConfirmDialog(final View view){

        AlertDialog.Builder builderForConfirm = new AlertDialog.Builder(getActivity());
        builderForConfirm.setTitle("ARE YOU SURE");
        builderForConfirm.setMessage("Are you sure you want to save the changes you made?");

        builderForConfirm.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                // Save into database here
                Log.d("Save Note", "Note title: "+ title.getText()+ "Note message: "+body.getText()+ "Note category: "+ savedNoteCategoryButton); // Save Note is the key
                // title.setText(title.getText());
                //body.setText(body.getText());

                // final String changedTitle = title.getText().toString();
                //final String changedBody = body.getText().toString();

                //title.setText(changedTitle, TextView.BufferType.EDITABLE);
                // body.setText(changedBody, TextView.BufferType.EDITABLE);

                // savedNoteCategoryButton

                // title.setText("ABC");

                String notetitle = title.getText().toString();
                String noteBody = body.getText().toString();
                String noteCategory;

                if(savedNoteCategoryButton==null)
                    noteCategory = "DEFAULT" ;
                else
                    noteCategory = savedNoteCategoryButton.toString();

                String textToBeWrittenIntoFile = notetitle+","+noteBody+","+noteCategory;

                saveNoteIntoFile(view, textToBeWrittenIntoFile);
                //getTextFromNoteFile();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

        });

        builderForConfirm.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                // NOTHING IS REQUIRED HERE
                getTextFromNoteFile();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

        });

        dialogForConfirm = builderForConfirm.create();
    }


    public void saveNoteIntoFile(View v, String textToBeWrittenIntoFile){
        final Context context = getActivity().getApplicationContext();
        try {
            //  File file = new File(context.getExternalFilesDir(null),noteTextFile);
            File file = new File(noteTextFile);

            //if (file.exists())
            // {
            FileOutputStream fileOutputStream = context.openFileOutput(noteTextFile, Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(textToBeWrittenIntoFile);
            outputStreamWriter.write("\n");
            outputStreamWriter.close();

            //}else{

            //  OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(noteTextFile, 0));
            //outputStreamWriter.write(textToBeWrittenIntoFile);
            //outputStreamWriter.close();
            // }
            Toast.makeText(context, "THE NOTE HAS BEEN SAVED.", Toast.LENGTH_LONG).show();


            //  if(!file.exists())
            //     file.createNewFile();

//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true /*append*/));
            //          bufferedWriter.write(textToBeWrittenIntoFile);
            //        bufferedWriter.close();

//            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, null);

        }catch(Throwable throwable){
            Toast.makeText(context, "EXCEPTION: "+throwable.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void getTextFromNoteFile() {
        final Context context = getActivity().getApplicationContext();
        try {
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
            }

        } catch (FileNotFoundException e) {
            Log.d("EXCEPTION: ", e.toString());
        } catch (Throwable throwable) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildColorPickerDialog() {
        //defining the color options we want to show in the menu
        final String[] colorPicker = new String[]{"Grey", "Pink", "Blue", "White", "Orange"};


        //now we will create an alert dialog window
        final AlertDialog.Builder colorPickerBuilder = new AlertDialog.Builder(getActivity());
        colorPickerBuilder.setTitle("Please pick a color");

        colorPickerBuilder.setSingleChoiceItems(colorPicker, 0, new DialogInterface.OnClickListener() {// we have given 0 as we want grey to be default color for our notes
            @Override
            public void onClick(DialogInterface dialog, int selectColor) {


                colorAlertDialogObject.cancel();// we will use this to dismiss our color menu.

                switch (selectColor) {
                    case 0:
                        savedColorCategoryButton = Colors.ColorCategory.GREY;
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.greyBackgroundColor));
                        break;

                    case 1:
                        savedColorCategoryButton = Colors.ColorCategory.PINK;
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.pinkBackgroundColor));
                        break;

                    case 2:
                        savedColorCategoryButton = Colors.ColorCategory.BLUE;
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.blueBackgroundColor));
                        break;

                    case 3:
                        savedColorCategoryButton = Colors.ColorCategory.WHITE;
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.whiteBackgroundColor));
                        break;

                    case 4:
                        savedColorCategoryButton = Colors.ColorCategory.ORANGE;
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.orangeBackgroundColor));
                        break;
                }


            }

        });
        colorAlertDialogObject = colorPickerBuilder.create();//we will use this to create our alert dialog window


    }
}
