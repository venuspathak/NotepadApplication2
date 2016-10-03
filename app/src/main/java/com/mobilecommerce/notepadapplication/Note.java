package com.mobilecommerce.notepadapplication;

/**
 * Created by User 1 on 02/10/2016.
 */

public class Note {

    private String title, description;
    private long noteId, dateCreated;
    private Category category;

    public enum Category {PERSONAL, FAMILY, SCHOOL, BILL, FOOD, DEFAULT, PARTY, SHOPPING, THOUGHTS }

    public Note(String title, String description, Category category) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.noteId = noteId;
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public long getNoteId() {
        return noteId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public String toString() {
        return "ID:" + noteId + "Title" + title + "Description" + description + "IconID" + category.name() + "Date: " + dateCreated;
    }

    public int getAssociatedDrawble() {
        return categoryToDrawble(category);
    }

    public static int categoryToDrawble(Category noteCategory) {
        switch (noteCategory) {
            case PERSONAL:
                return R.drawable.personal;

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
        }
        return categoryToDrawble(noteCategory);
    }
}
