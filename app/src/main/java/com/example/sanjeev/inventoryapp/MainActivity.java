package com.example.sanjeev.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjeev.inventoryapp.data.Contract;
import com.example.sanjeev.inventoryapp.data.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView columnCount, dataDisplay;
    Button addColumn, displayContents;
    // This will provide access to database
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        columnCount = findViewById(R.id.column_count_display);
        dataDisplay = findViewById(R.id.column_contents_display);
        addColumn = findViewById(R.id.add_new_column);
        addColumn.setOnClickListener(this);
        displayContents = findViewById(R.id.display_contents);
        displayContents.setOnClickListener(this);
        dbHelper = new DbHelper(this);
    }

    // Inserts data into database
    private void insertData() {
        // get writable access to write new data into the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // insertion of fake data
        values.put(Contract.NewEntry.COLUMN_PRODUCT_NAME, getString(R.string.product_title));
        values.put(Contract.NewEntry.COLUMN_PRICE, 700);
        values.put(Contract.NewEntry.COLUMN_QUANTITY, 5);
        values.put(Contract.NewEntry.COLUMN_SUPPLIER_NAME, getString(R.string.supplier_name));
        values.put(Contract.NewEntry.COLUMN_SUPPLIER_PHONE_NUMBER, getString(R.string.supplier_number));
        long newRowID = db.insert(Contract.NewEntry.TABLE_NAME, null, values);
        // Check if the row was inserted successfully from return value.
        if (newRowID == -1) {
            Toast.makeText(this, R.string.failed_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.succes_toast, Toast.LENGTH_SHORT).show();
        }

    }

    // Reads data from database
    // and displays number of columns to textview.
    private void readData() {
        // Get readable access to read data from the database.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Contract.NewEntry.TABLE_NAME,   // table name
                null, // null to select all columns from the table
                null,
                null,
                null,
                null,
                null,
                null);
        int numberOfColumns = cursor.getCount();   // Get Number of rows in table.
        cursor.close();                            // close the cursor after performing the action.
        // Update the value in textview
        columnCount.setText(String.format("%s%s", getString(R.string.table_count_display), String.valueOf(numberOfColumns)));
    }

    private void displayData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                Contract.NewEntry.COLUMN_PRODUCT_NAME
        };

        Cursor cursor = db.query(
                Contract.NewEntry.TABLE_NAME,       // Table Name
                projection,                         // only need names of product
                null,
                null,
                null,
                null,
                null
        );
        List<String> productNames = new ArrayList<String>();
        while(cursor.moveToNext()) {
            productNames.add(cursor.getString(cursor.getColumnIndexOrThrow(Contract.NewEntry.COLUMN_PRODUCT_NAME)));
        }
        cursor.close();
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<productNames.size(); i++){
            builder.append(productNames.get(i)).append("\n");
        }
        dataDisplay.setText(builder.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_new_column :
                // On button click insert dummy data and update the value in textview after insertion
                insertData();
                readData();
                break;
            case R.id.display_contents :
                displayData();
                break;
        }
    }
}
