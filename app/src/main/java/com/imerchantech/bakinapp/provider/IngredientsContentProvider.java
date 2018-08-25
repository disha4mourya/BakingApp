package com.imerchantech.bakinapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

import com.imerchantech.bakinapp.provider.IngredientsContract.*;
public class IngredientsContentProvider extends ContentProvider {


    public static final int INGREDIENTS = 100;
    public static final int INGREDIENT_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = RecipeContentProvider.class.getName();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(IngredientsContract.AUTHORITY, IngredientsContract.PATH_INGREDIENT, INGREDIENTS);
        uriMatcher.addURI(IngredientsContract.AUTHORITY, IngredientsContract.PATH_INGREDIENT + "/#", INGREDIENT_WITH_ID);
        return uriMatcher;
    }


    private RecipeDbHelper mRecipeDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case INGREDIENTS:

                long id = db.insert(IngredientsEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(IngredientsContract.IngredientsEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {


        final SQLiteDatabase db = mRecipeDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {

            case INGREDIENTS:
                retCursor = db.query(IngredientsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case INGREDIENT_WITH_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = db.query(IngredientsContract.IngredientsEntry.TABLE_NAME,
                        projection,
                        "_id=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int recipeDeleted;
        switch (match) {
            case INGREDIENT_WITH_ID:
                String id = uri.getPathSegments().get(1);
                recipeDeleted = db.delete(IngredientsEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (recipeDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return recipeDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int recipesUpdated;

        switch (match) {
            case INGREDIENTS:
                recipesUpdated = db.update(IngredientsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case INGREDIENT_WITH_ID:
                if (selection == null) selection = IngredientsEntry._ID + "=?";
                else selection += " AND " + IngredientsEntry._ID + "=?";
                String id = uri.getPathSegments().get(1);
                if (selectionArgs == null) selectionArgs = new String[]{id};
                else {
                    ArrayList<String> selectionArgsList = new ArrayList<String>();
                    selectionArgsList.addAll(Arrays.asList(selectionArgs));
                    selectionArgsList.add(id);
                    selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);
                }
                recipesUpdated = db.update(IngredientsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (recipesUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return recipesUpdated;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
