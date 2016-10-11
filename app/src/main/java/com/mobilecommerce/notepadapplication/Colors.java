package com.mobilecommerce.notepadapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;

/**
 * Created by Shivu on 2016-10-10.
 */

public class Colors {

    public enum ColorCategory {GREY, PINK, BLUE, WHITE, ORANGE};
    private ColorCategory backgroundColor;

    public Colors(ColorCategory backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public ColorCategory getBackgroundColor(){
        return backgroundColor;
    }
    public int getAssociatedDrawble() {
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
