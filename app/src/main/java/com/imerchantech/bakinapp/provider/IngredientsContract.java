package com.imerchantech.bakinapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class IngredientsContract {

    public static final String AUTHORITY = "com.imerchantech.bakinapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_INGREDIENT = "ingre";

    public static final long INVALID_INGRE_ID = -1;

    public static final class IngredientsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();


        public static final String TABLE_NAME = "ingre";
        public static final String COLUMN_INGRE_NAME = "ingreName";

    }
}
