package com.imerchantech.bakinapp;


import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.imerchantech.bakinapp.ingredient_list.IngredientListActivity;
import com.imerchantech.bakinapp.provider.RecipeContract;
import com.imerchantech.bakinapp.recipe_details.view.RecipeDetailsActivity;
import com.imerchantech.bakinapp.recipe_list.view.RecipeActivity;

public class RecipeWidgetProvider extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 long recipeId,  int appWidgetId) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;
        if (width < 300) {
            rv = getSingleRecipeRemoteView(context, recipeId);
        } else {
            rv = getGardenGridRemoteView(context);
        }
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeService.startActionUpdateRecipeWidgets(context);
    }


    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           long recipeId, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeId,  appWidgetId);
        }
    }

    private static RemoteViews getSingleRecipeRemoteView(Context context,  long recipeId) {

        Intent intent;
        if (recipeId == RecipeContract.INVALID_RECIPE_ID) {
            intent = new Intent(context, RecipeActivity.class);
        } else { // Set on click to open the corresponding detail activity
            Log.d(RecipeWidgetProvider.class.getSimpleName(), "recipeId=" + recipeId);
            intent = new Intent(context, IngredientListActivity.class);
            intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID, recipeId);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);


        views.setTextViewText(R.id.widget_recipe_name, String.valueOf(recipeId));

        views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);

        return views;
    }


    private static RemoteViews getGardenGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        // Set the IngredientListActivity intent to launch when clicked
        Intent appIntent = new Intent(context, IngredientListActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        // Handle empty gardens
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        RecipeService.startActionUpdateRecipeWidgets(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

}
