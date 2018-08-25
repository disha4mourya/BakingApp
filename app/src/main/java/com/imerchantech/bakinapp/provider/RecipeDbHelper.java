package com.imerchantech.bakinapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.imerchantech.bakinapp.provider.RecipeContract.*;
import com.imerchantech.bakinapp.provider.IngredientsContract.*;

public class RecipeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shushme.db";

    private static final int DATABASE_VERSION = 1;

    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE " + RecipeEntry.TABLE_NAME + " (" +
                RecipeEntry._ID + " INTEGER PRIMARY KEY," +
                RecipeEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                RecipeEntry.COLUMN_STEPS_LIST + " TIMESTAMP NOT NULL, " +
                RecipeEntry.COLUMN_INGREDIENT_LIST + " TIMESTAMP NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_RECIPE_TABLE);

        final String SQL_CREATE_INGREDIENT_TABLE = "CREATE TABLE " + IngredientsEntry.TABLE_NAME + " (" +
                IngredientsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IngredientsEntry.COLUMN_INGRE_NAME + " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
