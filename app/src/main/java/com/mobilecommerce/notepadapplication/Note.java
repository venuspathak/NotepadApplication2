package com.mobilecommerce.notepadapplication;

import android.icu.util.ULocale;


public class Note {
    private String title,description;
    private long noteId, dateCreated;
    private Category category;

    private enum Category {BILL, FAMILY, FOOD, MAIN, PARTY, PERSONAL, SCHOOL, SHOPPING, THOUGHTS}

    public Note(String title, String description, Category category) {
        this.title = title;
        this.description = description;
        this.category = category;

    }

    public String getTitle() {
        return title;
    }
    public  String getDescription() {
        return description;
    }
    public Category getCategory(){
        return category;
    }
    public long getDateCreated() {
        return dateCreated;
    }
    public String toString(){
        return "ID:" + noteId + "Title" + title + "Description" +description+ "IconID" + category.name() +
                 "Date:" + dateCreated;
    }
    public int getAssociatedDrawble() {
        return categoryToDrawble(category);
    }

    private static int categoryToDrawble(Category noteCategory) {
        switch(noteCategory) {
            case BILL:
                return R.drawable.bill;
            case FAMILY:
                return R.drawable.family;
            case FOOD:
                return R.drawable.food;
            case MAIN:
                return R.drawable.main;
            case PARTY:
                return R.drawable.party;
            case PERSONAL:
                return R.drawable.personal;
            case SCHOOL:
                return R.drawable.school;
            case SHOPPING:
                return R.drawable.shopping;
            case THOUGHTS:
                return R.drawable.thoughts;
        }
        return categoryToDrawble(noteCategory);
    }
}
