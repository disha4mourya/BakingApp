package com.imerchantech.bakinapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.imerchantech.bakinapp.provider.IngredientsContract;

import static com.imerchantech.bakinapp.provider.IngredientsContract.PATH_INGREDIENT;
import static com.imerchantech.bakinapp.provider.RecipeContract.BASE_CONTENT_URI;


public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri RECIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                RECIPE_URI,
                null,
                null,
                null,
                IngredientsContract.IngredientsEntry.COLUMN_INGRE_NAME
        );
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        int recipeTypeIndex = mCursor.getColumnIndex(IngredientsContract.IngredientsEntry.COLUMN_INGRE_NAME);
        String recipeName = mCursor.getString(recipeTypeIndex);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.widget_recipe_name, recipeName);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
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

