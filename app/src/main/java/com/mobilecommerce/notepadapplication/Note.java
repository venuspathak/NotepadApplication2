
package com.mobilecommerce.notepadapplication;


import android.view.View;

import java.io.InputStream;

public class Note {

    private String noteTitle, noteDescription, noteId;
    private long dateCreated;
    private Category noteCategory;
<<<<<<< HEAD
    private ColorCategory noteColorCategory;
    public static int trackerForNoteId = 0;
=======
>>>>>>> parent of cf6866e... Commiting Color Changes


    public enum Category {PERSONAL, FAMILY, SCHOOL, BILL, FOOD, DEFAULT, PARTY, SHOPPING, THOUGHTS }

<<<<<<< HEAD
    public Note(String noteTitle, String noteDescription, Category noteCategory, String noteId, ColorCategory noteColorCategory) {
=======
    public Note(String noteTitle, String noteDescription, Category noteCategory) {
>>>>>>> parent of cf6866e... Commiting Color Changes
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteCategory = noteCategory;
        this.noteId = noteId;
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return noteTitle;
    }

    public String getDescription() {
        return noteDescription;
    }

    public Category getCategory() {
        return noteCategory;
    }

    public String getNoteId() {
        return noteId;
    }

<<<<<<< HEAD
    public ColorCategory getColorCategory(){
        return noteColorCategory;
    }

=======
>>>>>>> parent of cf6866e... Commiting Color Changes
    public long getDateCreated() {
        return dateCreated;
    }

    public String toString() {
<<<<<<< HEAD
        return "ID:" + noteId + "Title" + noteTitle + "Description" + noteDescription + "IconID" + noteCategory.name() +"Color "+noteColorCategory.name()+ "Date: " + dateCreated;
=======
        return "ID:" + noteId + "Title" + noteTitle + "Description" + noteDescription + "IconID" + noteCategory.name() + "Date: " + dateCreated;
>>>>>>> parent of cf6866e... Commiting Color Changes
    }

    public int getAssociatedDrawble() {
        return categoryToDrawble(noteCategory);
    }

    public static int categoryToDrawble(Category noteCategory) {
        switch (noteCategory) {

            case FAMILY:
                return R.drawable.family;

            case SCHOOL:
                return R.drawable.school;

            case BILL:
                return R.drawable.bill;

            case FOOD:
                return R.drawable.food;

            case DEFAULT:
                return R.drawable.main;

            case PARTY:
                return R.drawable.party;

            case SHOPPING:
                return R.drawable.shopping;

            case THOUGHTS:
                return R.drawable.thoughts;

            case PERSONAL:
                return R.drawable.personal;


        }
<<<<<<< HEAD
        return categoryToDrawbleCategory(noteCategory);
    }


    public int getAssociatedDrawbleColorCategory() {
        return categoryToBackgroundColor(noteColorCategory);
    }

    public static int categoryToBackgroundColor(ColorCategory backgroundColor){
        switch (backgroundColor) {
            case GREY:
                return R.color.greyBackgroundColor;

            case PINK:
                return R.color.pinkBackgroundColor;

            case WHITE:
                return R.color.whiteBackgroundColor;

            case ORANGE:
                return R.color.orangeBackgroundColor;

            case BLUE:
                return R.color.blueBackgroundColor;

        }
        return categoryToBackgroundColor(backgroundColor);
=======
        return categoryToDrawble(noteCategory);
>>>>>>> parent of cf6866e... Commiting Color Changes
    }


}