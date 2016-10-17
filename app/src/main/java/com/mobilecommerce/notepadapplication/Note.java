
package com.mobilecommerce.notepadapplication;


import android.view.View;

import java.io.InputStream;

public class Note {

    private String noteTitle, noteDescription, noteId;
    private long dateCreated;
    private Category noteCategory;

    private Colors.ColorCategory noteColorCategory;
    public static int trackerForNoteId = 0;


    public enum Category {PERSONAL, FAMILY, SCHOOL, BILL, FOOD, DEFAULT, PARTY, SHOPPING, THOUGHTS }


    public Note(String noteTitle, String noteDescription, Category noteCategory, String noteId, Colors noteColorCategory) {

    public Note(String noteTitle, String noteDescription, Category noteCategory) {


    public Note(String noteTitle, String noteDescription, Category noteCategory, String noteId) {

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


    public ColorCategory getColorCategory(){
        return noteColorCategory;
    }



    public long getDateCreated() {
        return dateCreated;
    }

    public String toString() {

        return "ID:" + noteId + "Title" + noteTitle + "Description" + noteDescription + "IconID" + noteCategory.name() +"Color "+noteColorCategory.name()+ "Date: " + dateCreated;

        return "ID:" + noteId + "Title" + noteTitle + "Description" + noteDescription + "IconID" + noteCategory.name() + "Date: " + dateCreated;


        return "ID:" + noteId + "Title" + noteTitle + "Description" + noteDescription + "IconID" + noteCategory.name() +"Color"+noteColorCategory.name()+ "Date: " + dateCreated;

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


    private ColorCategory backgroundColor;

    public void ColorCategory(ColorCategory backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public ColorCategory getBackgroundColor(){
        return backgroundColor;
    }
    public int getAssociatedDrawbleColorCategory() {
        return categoryToBackgroundColor(backgroundColor);
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

        return categoryToDrawble(noteCategory);

    }


}