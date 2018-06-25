package com.example.sanjeev.inventoryapp;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sanjeev.inventoryapp.data.Contract;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    FloatingActionButton fab;
    ListView listView;
    private static final int PRODUCT_LOADER = 0;
    ProductCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddNewProductActivity.class);
                startActivity(in);
            }
        });

        listView = findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.emptyView);
        listView.setEmptyView(emptyView);
        adapter = new ProductCursorAdapter(this, null);
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent in = new Intent(getApplicationContext(), AddNewProductActivity.class);
                Uri currentUri = ContentUris.withAppendedId(Contract.NewEntry.CONTENT_URI, id);
                in.setData(currentUri);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all :
                getContentResolver().delete(Contract.NewEntry.CONTENT_URI, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                Contract.NewEntry._ID,
                Contract.NewEntry.COLUMN_PRODUCT_NAME,
                Contract.NewEntry.COLUMN_PRICE,
                Contract.NewEntry.COLUMN_QUANTITY};
        return new CursorLoader(this,
                Contract.NewEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
