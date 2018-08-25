package com.imerchantech.bakinapp.provider;


import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeContract {


    public static final String AUTHORITY = "com.imerchantech.bakinapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPE = "recipes";

    public static final long INVALID_RECIPE_ID = -1;

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_RECIPE_NAME = "recipeType";
        public static final String COLUMN_STEPS_LIST = "createdAt";
        public static final String COLUMN_INGREDIENT_LIST = "lastWateredAt";
    }

}
