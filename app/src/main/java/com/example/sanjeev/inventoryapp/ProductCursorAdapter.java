package com.example.sanjeev.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.sanjeev.inventoryapp.data.Contract;

public class ProductCursorAdapter extends CursorAdapter implements View.OnClickListener {

    public ProductCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView productNameView = view.findViewById(R.id.product_name);
        TextView productPriceView = view.findViewById(R.id.price);
        TextView productQuantityView = view.findViewById(R.id.quantity);
        String productName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.NewEntry.COLUMN_PRODUCT_NAME));
        int productPrice = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.NewEntry.COLUMN_PRICE));
        int productQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.NewEntry.COLUMN_QUANTITY));

        Button button = view.findViewById(R.id.button_sell);
        button.setOnClickListener(this);

        productNameView.setText(productName);
        productPriceView.setText(String.format("Price : Rs. %s", String.valueOf(productPrice)));
        productQuantityView.setText(String.format("Available : %s", String.valueOf(productQuantity)));
    }

    @Override
    public void onClick(View v) {
        
    }
}
