package com.example.sanjeev.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanjeev.inventoryapp.data.Contract;

public class AddNewProductActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText productName, productCost, supplierName, supplierNumber, productQuantity;
    private static final int EXISTING_PRODUCT_LOADER = 0;
    private boolean ProuctChanged = false;
    Uri currentUri;
    private View.OnTouchListener mTouchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ProuctChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        Intent in = getIntent();
        currentUri = in.getData();

        if(currentUri == null){
            setTitle("Add a Product");
        }
        else{
            setTitle("Edit Product");
            getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

        productName = findViewById(R.id.in_product_name);
        productCost = findViewById(R.id.in_product_cost);
        supplierName = findViewById(R.id.in_supplier_name);
        supplierNumber = findViewById(R.id.in_supplier_number);
        productQuantity = findViewById(R.id.in_quantity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertProduct();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertProduct() {
        ContentValues values = new ContentValues();
        values.put(Contract.NewEntry.COLUMN_PRODUCT_NAME, productName.getText().toString().trim());
        values.put(Contract.NewEntry.COLUMN_SUPPLIER_NAME, supplierName.getText().toString().trim());
        values.put(Contract.NewEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierNumber.getText().toString().trim());
        values.put(Contract.NewEntry.COLUMN_PRICE, Integer.parseInt(productCost.getText().toString().trim()));
        values.put(Contract.NewEntry.COLUMN_QUANTITY, Integer.parseInt(productQuantity.getText().toString().trim()));
        try{
            getContentResolver().insert(Contract.NewEntry.CONTENT_URI, values);
            finish();
        }
        catch(Exception e){
            Toast.makeText(this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                Contract.NewEntry._ID,
                Contract.NewEntry.COLUMN_PRODUCT_NAME,
                Contract.NewEntry.COLUMN_PRICE,
                Contract.NewEntry.COLUMN_SUPPLIER_NAME,
                Contract.NewEntry.COLUMN_SUPPLIER_NAME,
                Contract.NewEntry.COLUMN_SUPPLIER_PHONE_NUMBER,
                Contract.NewEntry.COLUMN_QUANTITY};
        return new CursorLoader(this,
                currentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor == null || cursor.getCount() < 1)
            return;
        if(cursor.moveToFirst()){
            productName.setText(cursor.getString(cursor.getColumnIndex(Contract.NewEntry.COLUMN_PRODUCT_NAME)));
            supplierName.setText(cursor.getString(cursor.getColumnIndex(Contract.NewEntry.COLUMN_SUPPLIER_NAME)));
            supplierNumber.setText(cursor.getString(cursor.getColumnIndex(Contract.NewEntry.COLUMN_SUPPLIER_PHONE_NUMBER)));
            productCost.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.NewEntry.COLUMN_PRICE))));
            productQuantity.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.NewEntry.COLUMN_QUANTITY))));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productName.setText("");
        supplierName.setText("");
        supplierNumber.setText("");
        productCost.setText("");
        productQuantity.setText("");
    }
}
