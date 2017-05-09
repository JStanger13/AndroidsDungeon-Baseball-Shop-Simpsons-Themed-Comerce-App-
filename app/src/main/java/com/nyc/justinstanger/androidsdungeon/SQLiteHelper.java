package com.nyc.justinstanger.androidsdungeon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.logo;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.COLUMN_DESCRIPTION;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.COLUMN_ID;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.COLUMN_IMAGE;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.COLUMN_NAME;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.COLUMN_PRICE;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.COLUMN_QUANTITY;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.COLUMN_TYPE;
import static com.nyc.justinstanger.androidsdungeon.SQLiteHelper.ItemsTable.TABLE_NAME;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "androids_dungeon.db";
    private static final String TAG = "SQLiteHelper";

    public static abstract class ItemsTable {
        public static final String TABLE_NAME = "itemsTable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_QUANTITY = "quantity";

    }


    private static final String SQL_CREATE_ENTRIES_ITEMS = "CREATE TABLE " +
            TABLE_NAME + " (" +
            ItemsTable.COLUMN_ID + " INTEGER PRIMARY KEY," +
            ItemsTable.COLUMN_TYPE + " TEXT," +
            ItemsTable.COLUMN_NAME + " TEXT," +
            ItemsTable.COLUMN_DESCRIPTION + " TEXT," +
            ItemsTable.COLUMN_PRICE + " INTEGER," +
            ItemsTable.COLUMN_IMAGE + "TEXT " + ItemsTable.COLUMN_QUANTITY + " INTEGER" + ")";

    private static final String SQL_DELETE_ENTRIES_ITEMS = "DROP TABLE IF EXISTS " +
            TABLE_NAME;

    private static SQLiteHelper sInstance;

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_ITEMS);
        onCreate(db);
    }

    public SQLiteHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }


    public List<String> searchByType() {

        SQLiteDatabase db = getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();


        Cursor cursor = builder.query(db, new String[]{},
                ItemsTable.COLUMN_TYPE + "= ?", new String[]{" comics "}, null, null, null);
        List<String> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {


                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                list.add(type);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }

    public List<Item> getListOfItemsInCart(Context context) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ItemsTable.TABLE_NAME,
                null,
                ItemsTable.COLUMN_QUANTITY + " > ?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null);

        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));
                //String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());


                int price = cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY));
                long id = cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_ID));

                Item item = new Item(name, type, description, image, price, id, quantity);

                //context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName());

                list.add(item);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }

    public void addToCart(Item item, int add) {
        int currentQuantity = item.getQuantity() + add;
        item.setQuantity(currentQuantity);

        updateCart(item.getId(), currentQuantity);
    }

    public void deleteFromCart(Item item, int delete) {
        int currentQuantity = item.getQuantity() - delete;
        if (currentQuantity < 0) item.setQuantity(0);
        else item.setQuantity(currentQuantity);
        updateCart(item.getId(), currentQuantity);
    }

    public void updateCart(long id, int currentQuantity) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemsTable.COLUMN_QUANTITY, currentQuantity);

        db.update(ItemsTable.TABLE_NAME,
                values,
                ItemsTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }


    public Item getItemById(long id, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ItemsTable.TABLE_NAME,   // table name
                null,                               // columns (null is like SELECT *)
                COLUMN_ID + " = ?",                    // selection (the WHERE clause; ? is placeholder)
                new String[]{String.valueOf(id)},   // selectionArgs (plug into the ? placeholder)
                null,                               // groupBy
                null,                               // having
                null);                              // orderBy

        Item item = null;

        // If the table did contain a row with primary key = id, then that will be
        // the one (and only) row in the cursor
        if (cursor.moveToFirst()) {
            // Get the values out of that row and make a Book object from them

                    String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                    String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                    String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));
                    //String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                    int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());


                    int price = cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE));
                    int quantity = cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY));

                    item = new Item(name, type, description, image, price, id, quantity);

        }

        cursor.close();
        return item; // could return NULL if the cursor had zero rows!
    }

    public List<Item> getAllComics(Context context) {
        Log.d(TAG, "getAllComics: starting...");
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ItemsTable.TABLE_NAME, null,
                ItemsTable.COLUMN_TYPE + "= ?", new String[]{"comic"}, null, null, null);

        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            Log.d(TAG, "getAllComics: cursor is not empty");
            while (!cursor.isAfterLast()) {

      //        String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());


                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));

                int price = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
                int quantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY)));

                Item item = new Item(name, type, description, image, price, id, quantity);




                Log.d(TAG, "getAllComics: " + name);
                list.add(item);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }

    public List<Item> getAllMovies(Context context) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ItemsTable.TABLE_NAME, null,
                ItemsTable.COLUMN_TYPE + "= ?", new String[]{"movie"}, null, null, null);

        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());

                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));

                int price = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
                int quantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY)));

                Item item = new Item(name, type, description, image, price, id, quantity);
                list.add(item);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }

    public List<Item> getAllCollectables(Context context) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ItemsTable.TABLE_NAME, null,
                ItemsTable.COLUMN_TYPE + "= ?", new String[]{"collectable"}, null, null, null);

        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());



                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));

                int price = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
                int quantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY)));

                Item item = new Item(name, type, description, image, price, id, quantity);
                list.add(item);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }


    public List<Item> getAllTypes(Context context) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ItemsTable.TABLE_NAME, null,
                null, null, null, null, null);

        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());

                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));

                int price = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));

                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
                int quantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY)));

                Item item = new Item(name, type, description, image, price, id, quantity);
                list.add(item);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }


    public void resetAllTypes() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ItemsTable.TABLE_NAME, null,
                null, null, null, null, null);

        List<Long> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                long id = cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_ID));
                list.add(id);
                cursor.moveToNext();
            }
        }


        for (int i = 0; i < list.size(); i++) {
            updateCart(list.get(i), 0);
        }

        cursor.close();
    }


    public List<Item> searchSimilarNames(String query, Context context) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ItemsTable.TABLE_NAME, null,
                "(" + ItemsTable.COLUMN_NAME +  " LIKE ?" + ")" + " OR " + "(" + ItemsTable.COLUMN_DESCRIPTION +
                        " LIKE ?" + ")", new String[]{ "%" + query + "%", "%" + query + "%" }, null, null, COLUMN_NAME, null);


        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());


                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));

                int price = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
                int quantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY)));

                Item item = new Item(name, type, description, image, price, id, quantity);
                list.add(item);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }


    public List<Item> searchForprice(String query, Context context) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ItemsTable.TABLE_NAME, // a. table
                null, // b. column names
                ItemsTable.COLUMN_PRICE + " < ?", // c. selections
                new String[]{query}, // d. selections args
                null, // e. group by
                null, // f. having
                COLUMN_PRICE, // g. order by
                null); // h. limit


        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //String image = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE));
                int image=  context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)), "drawable", context.getPackageName());


                String type = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION));

                int price = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
                int quantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY)));

                Item item = new Item(name, type, description, image, price, id, quantity);
                list.add(item);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }
}