package com.example.shop_thoi_trang_mobile.activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shop_thoi_trang_mobile.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB";
    private static final String TABLE_PRODUCTS = "products";

    private static final String COLUMN_PRODUCT_ID = "productId";
    private static final String COLUMN_PRODUCT_NAME = "productName";
    private static final String COLUMN_PRODUCT_CODE = "productCode";
    private static final String COLUMN_PRODUCT_CATEGORY = "productCategory";
    private static final String COLUMN_PRODUCT_BRAND = "productBrand";
    private static final String COLUMN_PRODUCT_PRICE = "productPrice";
    private static final String COLUMN_PRODUCT_QUANTITY = "productQuantity";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "productDescription";
    private static final String COLUMN_PRODUCT_IMAGE = "productImage";
    private static final String COLUMN_PRODUCT_STATUS = "productStatus";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRODUCT_CODE + " TEXT,"
                + COLUMN_PRODUCT_CATEGORY + " TEXT,"
                + COLUMN_PRODUCT_BRAND + " TEXT,"
                + COLUMN_PRODUCT_PRICE + " TEXT,"
                + COLUMN_PRODUCT_QUANTITY + " INTEGER,"
                + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
                + COLUMN_PRODUCT_IMAGE + " TEXT,"
                + COLUMN_PRODUCT_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Adding a new product
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_PRODUCT_CODE, product.getProductCode());
        values.put(COLUMN_PRODUCT_CATEGORY, product.getProductCategory());
        values.put(COLUMN_PRODUCT_BRAND, product.getProductBrand());
        values.put(COLUMN_PRODUCT_PRICE, product.getProductPrice().toString());
        values.put(COLUMN_PRODUCT_QUANTITY, product.getProductQuantity());
        values.put(COLUMN_PRODUCT_DESCRIPTION, product.getProductDescription());
        values.put(COLUMN_PRODUCT_IMAGE, product.getProductImage());
        values.put(COLUMN_PRODUCT_STATUS, product.getProductStatus());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    // Getting all products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_CODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_BRAND)),
                        new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE))),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_STATUS))
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
}
