package com.mobilecommerce.notepadapplication;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment {

    private ImageButton noteCategoryButton, noteColorCategoryButton;
    private Note.Category savedNoteCategoryButton;
    private Colors.ColorCategory savedColorCategoryButton;
    public AlertDialog categoryAlertDialogObject, dialogForConfirm, colorAlertDialogObject, optionsAlertDialogObject;
    private EditText title, body;
    private static final String categoryModified = "Modified Category";
    public Boolean newNote = false;
    private static final String noteTextFile = "noteTextFile10.txt";
    private EditText textEditor;
    private Note.Category noteCategoryFinal;
    private static final int PICK_IMAGE = 100;
    private ImageView imageView;
    private ImageButton editNoteGallery, editNoteCamera;
    private RelativeLayout relativeLayout;
    private String userHasChosen;
    Uri uri;
    private static int LOAD_CHOSEN_IMAGE = 1;
    private static int REQUEST_CAMERA = 0;
    private static final int EXTERNAL_STORAGE_REQUEST = 1;
    private String finalImagePath;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


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
        editNoteCamera = (ImageButton) fragmentLayout.findViewById(R.id.editNoteCamera);
        noteColorCategoryButton = (ImageButton)fragmentLayout.findViewById(R.id.colorPicker);
        editNoteGallery = (ImageButton) fragmentLayout.findViewById(R.id.editNoteGallery);
        relativeLayout = (RelativeLayout) fragmentLayout.findViewById(R.id.backGroundColor);



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
        verifyNecessaryStoragePermissionsForImages(getActivity());
        //buildCameraGalleryOptionsDialog();


        //setting a listener on the note category button
        noteCategoryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                categoryAlertDialogObject.show();
            }
        });
        //setting listerner on the gallery button
        editNoteGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent(v);
            }
        });

        //setting listerner on the camera button
        editNoteCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(categoryModified, savedNoteCategoryButton);
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

//           MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, null);

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


    public void galleryIntent(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, LOAD_CHOSEN_IMAGE);

    }
    public void cameraIntent(){
        Intent cameraIntent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    public static void verifyNecessaryStoragePermissionsForImages(Activity activity) {
        //Checking if we have write external storage permissions
        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissions != PackageManager.PERMISSION_GRANTED) {
            // We don't have permissions therefore getting permissions from the user
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,EXTERNAL_STORAGE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == LOAD_CHOSEN_IMAGE && resultCode == Activity.RESULT_OK && data != null) {

                Uri selectedImage = data.getData();
                Log.d("Uri", selectedImage.toString());

                String[] filePathColumn = {MediaStore.Images.Media.DATA};


                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();


                finalImagePath = cursor.getString(column_index);
                cursor.close();

                imageView.setImageBitmap(BitmapFactory.decodeFile(finalImagePath));

            } else if(requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data!= null){
                {   /*
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);



                    File destination = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".jpg");

                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageView.setImageBitmap(thumbnail);
                    */
                    Bitmap cameraPhoto = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(cameraPhoto);

                    Uri uri = getCameraImageUri(getContext(), cameraPhoto);

                    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    finalImagePath = cursor.getString(column_index);

                }
            } else
                Toast.makeText(getActivity(), "You haven't picked an image", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_LONG).show();
        }

    }
    public Uri getCameraImageUri(Context context, Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        finalImagePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "Camera Image", null);
        return Uri.parse(finalImagePath);
    }

}

