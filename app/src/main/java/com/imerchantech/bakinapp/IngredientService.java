package com.imerchantech.bakinapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.imerchantech.bakinapp.provider.IngredientsContract;

import static com.imerchantech.bakinapp.provider.IngredientsContract.PATH_INGREDIENT;
import static com.imerchantech.bakinapp.provider.RecipeContract.BASE_CONTENT_URI;
import static com.imerchantech.bakinapp.provider.RecipeContract.INVALID_RECIPE_ID;


public class IngredientService extends IntentService {

    public static final String ACTION_SHOW_INGREDIENTS = "com.example.bakinapp.action.show_ingredients";
    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "com.example.bakinapp.action.update_ingredients_widgets";
    public static final String EXTRA_RECIPE_ID = "com.example.bakinapp.extra.RECIPE_ID";

    public IngredientService() {
        super("IngredientService");
    }


    public static void startActionUpdateRecipeWidgets(Context context) {
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_INGREDIENTS.equals(action)) {
                final long recipeId = intent.getLongExtra(EXTRA_RECIPE_ID,
                        INVALID_RECIPE_ID);
                handleActionShowIngredients(recipeId);
            } else if (ACTION_UPDATE_RECIPE_WIDGETS.equals(action)) {
                handleActionUpdateRecipeWidgets();
            }
        }
    }


    private void handleActionShowIngredients(long recipeId) {

        ContentValues contentValues = new ContentValues();
        long timeNow = System.currentTimeMillis();
        contentValues.put(IngredientsContract.IngredientsEntry.COLUMN_INGRE_NAME, timeNow);

        startActionUpdateRecipeWidgets(this);
    }

    private void handleActionUpdateRecipeWidgets() {
        Uri RECIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
        Cursor cursor = getContentResolver().query(
                RECIPE_URI,
                null,
                null,
                null,
                IngredientsContract.IngredientsEntry.COLUMN_INGRE_NAME
        );

        String recipeId = "";
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex(IngredientsContract.IngredientsEntry.COLUMN_INGRE_NAME);

            recipeId = cursor.getString(idIndex);
            cursor.close();

        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        IngredientWidgetProvider.updateRecipeWidgets(this, appWidgetManager, recipeId, appWidgetIds);
    }
}
