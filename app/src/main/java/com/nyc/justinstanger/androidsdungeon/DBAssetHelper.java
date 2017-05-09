package com.nyc.justinstanger.androidsdungeon;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by justinstanger on 4/10/17.
 */

public class DBAssetHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "androids_dungeon.db";
    private static final int DATABASE_VERSION = 1;

    public DBAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}
