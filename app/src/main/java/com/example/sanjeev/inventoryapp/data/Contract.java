package com.example.sanjeev.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    private Contract() {
        // Empty constructor to avoid initialisation
    }

    public static final String CONTENT_AUTHORITY = "com.example.sanjeev.inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "product";

    // This class defines constants for the table
    public static final class NewEntry implements BaseColumns {

        // Unique ID for each product
        public static final String _ID = BaseColumns._ID;

        // Name of the table
        public static final String TABLE_NAME = "product";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        // Column - Product Name
        // DataType - String
        public static final String COLUMN_PRODUCT_NAME = "product_name";

        // Column - Price
        // DataType - INTEGER
        public static final String COLUMN_PRICE = "price";

        // Column - Quantity
        // DataType - INTEGER
        public static final String COLUMN_QUANTITY = "quantity";

        // Column - Supplier Name
        // DataType - STRING
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";

        // Column - Supplier Phone Number
        // DataType - STRING
        // Phone number can be very long, so its a better idea to store it in a string
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";


    }

}
