package com.example.bakinapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakinapp.recipe_list.entities.AllRecipeEntity;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.google.gson.Gson;
import com.imerchantech.bakinapp.R;

import java.util.List;

import static com.example.bakinapp.app.BakingApp.sharedPreferences;
import static com.example.bakinapp.network.Constants.ALLRECIPEENTITY;

public class RecipeAppWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRecipesViewsFactory(this.getApplicationContext());
    }
}

class GridRecipesViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    AllRecipeEntity allRecipeEntity;
    List<RecipeEntity> recipeEntityList;

    public GridRecipesViewsFactory(Context applicationContext) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {
        String allRecipeEntityString = sharedPreferences.getString(ALLRECIPEENTITY, null);
        allRecipeEntity = new Gson().fromJson(allRecipeEntityString, AllRecipeEntity.class);
        recipeEntityList = allRecipeEntity.getRecipeEntityList();
        if (recipeEntityList!=null)
        Log.d("widgetData",""+recipeEntityList.size());
        else
            Log.d("widgetData",""+"inElse");

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {

        String allRecipeEntityString = sharedPreferences.getString(ALLRECIPEENTITY, null);
        allRecipeEntity = new Gson().fromJson(allRecipeEntityString, AllRecipeEntity.class);
        recipeEntityList = allRecipeEntity.getRecipeEntityList();
        // Get all plant info ordered by creation time
      /*  Uri PLANT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLANTS).build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                PLANT_URI,
                null,
                null,
                null,
                PlantContract.PlantEntry.COLUMN_CREATION_TIME
        );*/
    }

    @Override
    public void onDestroy() {
       /* mCursor.close();*/
    }

    @Override
    public int getCount() {
      /*  if (mCursor == null) return 0;*/
        return recipeEntityList.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
      /*  if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        int idIndex = mCursor.getColumnIndex(PlantContract.PlantEntry._ID);
        int createTimeIndex = mCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_CREATION_TIME);
        int waterTimeIndex = mCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME);
        int plantTypeIndex = mCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_PLANT_TYPE);

        long plantId = mCursor.getLong(idIndex);
        int plantType = mCursor.getInt(plantTypeIndex);
        long createdAt = mCursor.getLong(createTimeIndex);
        long wateredAt = mCursor.getLong(waterTimeIndex);
        long timeNow = System.currentTimeMillis();*/

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_app_widget);

        RecipeEntity recipeEntity=recipeEntityList.get(position);
        // Update the plant image
       // int imgRes = PlantUtils.getPlantImageRes(mContext, timeNow - createdAt, timeNow - wateredAt, plantType);
      //  views.setImageViewResource(R.id.widget_plant_image, imgRes);
        views.setTextViewText(R.id.appwidget_text,recipeEntity.getName());
        // Always hide the water drop in GridView mode
      //  views.setViewVisibility(R.id.widget_water_button, View.GONE);

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
      /*  Bundle extras = new Bundle();
        extras.putLong(PlantDetailActivity.EXTRA_PLANT_ID, plantId);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_plant_image, fillInIntent);*/

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
