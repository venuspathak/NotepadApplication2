
package com.mobilecommerce.notepadapplication;


import android.view.View;

import java.io.InputStream;

public class Note {

    private String noteTitle, noteDescription;
    private long noteId, dateCreated;
    private Category noteCategory;

    public enum Category {PERSONAL, FAMILY, SCHOOL, BILL, FOOD, DEFAULT, PARTY, SHOPPING, THOUGHTS }

    public Note(String noteTitle, String noteDescription, Category noteCategory) {
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

    public long getNoteId() {
        return noteId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public String toString() {
        return "ID:" + noteId + "Title" + noteTitle + "Description" + noteDescription + "IconID" + noteCategory.name() + "Date: " + dateCreated;
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
        return categoryToDrawble(noteCategory);
    }


}