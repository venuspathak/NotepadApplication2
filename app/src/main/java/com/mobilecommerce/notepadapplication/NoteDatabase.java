package com.mobilecommerce.notepadapplication;

import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User 1 on 06/10/2016.
 */

public class NoteDatabase {

    //private int id;
    private int identifier; // This identifier's value will be 0 if only noteContent is specified, 1 if only noteTitle is specified
    // and 2 if both noteTitle and noteContent are specified
    private String noteTitle;
    private String noteContent;
  //  private Map<String, byte[]> noteImage = new HashMap<String, byte[]>(); // This variable is for images
    private ImageView noteImage;


    public NoteDatabase(){} // constructor with no arguments

    public NoteDatabase(int identifier, String noteTitleOrContent, ImageView noteImage){
       // this.id = id;     // constructor with either title or content and image

        if(identifier==0)
            this.noteContent=noteTitleOrContent;
        else if (identifier==1)
            this.noteTitle=noteTitle;
        this.noteTitle=noteTitleOrContent;

        this.noteImage=noteImage;
    }

    public NoteDatabase(int identifier, String noteTitleOrContent){
        // this.id = id;     // constructor with either title or content but no image

        if(identifier==0)
            this.noteContent=noteTitleOrContent;
        else if (identifier==1)
            this.noteTitle=noteTitle;
        this.noteTitle=noteTitleOrContent;

    }


    public NoteDatabase(String noteTitle, String noteContent){
        this.noteTitle=noteTitle;
        this.noteContent=noteContent;
    }

    public NoteDatabase(int identifier, String noteTitle, String noteContent, ImageView noteImage){
       // this.id = id;     // constructor with all title, content and image
        this.noteContent=noteContent;
        this.noteTitle=noteTitle;
        this.noteImage=noteImage;
    }

   // public void setID(int id){
   //     this.id=id; // A method to set id
   // }

   // public int getId(){
     //   return this.id;   // A method to return id
    //}

    public void setTitle(String noteTitle){
        this.noteTitle=noteTitle; // A method to set note title
    }

    public String getTitle(){
        return this.noteTitle;   // A method to return note title
    }

    public void setContent(String noteContent){
        this.noteContent=noteContent; // A method to set note content
    }

    public String getContent(){
        return this.noteContent;   // A method to return note content
    }

    public void setImage(ImageView noteImage){
        this.noteImage=noteImage; // A method to set note image
     }

    public ImageView getImage(){
        return this.noteImage;   // A method to return note image
    }

}
