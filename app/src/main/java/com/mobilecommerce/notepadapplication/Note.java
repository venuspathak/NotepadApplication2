
package com.mobilecommerce.notepadapplication;

public class Note {

    private String noteTitle, noteDescription;
    private long noteId, dateCreated;
    private Category noteCategory;
    private ColorCategory noteColorCategory;


    public enum Category {PERSONAL, FAMILY, SCHOOL, BILL, FOOD, DEFAULT, PARTY, SHOPPING, THOUGHTS }
    public enum ColorCategory {GREY, PINK, BLUE, WHITE, ORANGE}

    public Note(String noteTitle, String noteDescription, Category noteCategory, ColorCategory noteColorCategory) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteCategory = noteCategory;
        this.noteId = noteId;
        this.dateCreated = dateCreated;
        this.noteColorCategory = noteColorCategory;
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

    public ColorCategory getColorCategory(){
        return noteColorCategory;
    }


    public long getDateCreated() {
        return dateCreated;
    }

    public String toString() {
        return "ID:" + noteId + "Title" + noteTitle + "Description" + noteDescription + "IconID" + noteCategory.name() +"Color"+noteColorCategory.name()+ "Date: " + dateCreated;
    }

    public int getAssociatedDrawbleCategory() {
        return categoryToDrawbleCategory(noteCategory);
    }

    public static int categoryToDrawbleCategory(Category noteCategory) {
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
    }


}