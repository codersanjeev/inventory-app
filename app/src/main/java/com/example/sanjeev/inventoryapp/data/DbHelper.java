package com.example.sanjeev.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    // Name of the database file
    public static final String DATABASE_NAME = "inventory.db";

    // version of the database
    // must be changed on change of database schema
    public static final int DATABASE_VERSION = 1;

    // Constructor
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query which is to be executed.
        String SQL_CREATE_TABLE = "CREATE TABLE " + Contract.NewEntry.TABLE_NAME + " ("
                + Contract.NewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.NewEntry.COLUMN_PRODUCT_NAME + " TEXT, "
                + Contract.NewEntry.COLUMN_PRICE + " INTEGER, "
                + Contract.NewEntry.COLUMN_QUANTITY + " INTEGER, "
                + Contract.NewEntry.COLUMN_SUPPLIER_NAME + " TEXT, "
                + Contract.NewEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " TEXT);";

        // Execute the sql statement
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // empty for now
    }
}
