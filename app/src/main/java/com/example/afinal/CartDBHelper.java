package com.example.afinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CartDBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static final String DB_NAME = "cart.db";

    public CartDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성이 될 때 호출

        // 데이터베이스 -> 테이블 -> 컬럼 -> 값

        // 첫 실행(onCreate)시 테이블이 존재하면 삭제
//        db.execSQL("DROP TABLE IF EXISTS Cart");

        // 데이터베이스 생성. IF NOT EXISTS : 테이블이 이미 존재하면 실행하지 않음
        db.execSQL("CREATE TABLE Cart (id INTEGER PRIMARY KEY AUTOINCREMENT, image INTEGER NOT NULL, name TEXT NOT NULL, option TEXT NOT NULL, count INTEAGER NOT NULL, price INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS Cart");
//            onCreate(db);
        }
    }

    // SELECT 문
    public ArrayList<CartData> getMenuList(){
        ArrayList<CartData> cartDatas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cart", null);
        if(cursor.getCount() != 0){
            // 데이터가 있을 때. 조회 가능
            while (cursor.moveToNext()){        // 데이터베이스 값들을 다음 값이 없을때까지 반복

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow("image"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String option = cursor.getString(cursor.getColumnIndexOrThrow("option"));
                int count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

                CartData cartData = new CartData();
                cartData.setId(id);
                cartData.setCart_image(image);
                cartData.setCart_name(name);
                cartData.setCart_option(option);
                cartData.setCart_count(count);
                cartData.setCart_price(price);
                cartDatas.add(cartData);
            }
        }
        cursor.close();

        return cartDatas;
    }

    // INSERT 문
    public void InsertCart(int _image, String _name, String _option, int _count, int _price){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Cart (image, name, option, count, price) VALUES ('" + _image + "', '" + _name + "', '" + _option + "','" + _count + "','" + _price + "');");
    }

    // UPDATE 문
    public void UpdateCart(String _name, int _price, String _explan, String _beforename){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Cart SET name = '"+ _name + "', price = '"+ _price + "', explan = '"+ _explan + "' WHERE name = '" + _beforename + "'");
    }

    public void UpdateCount(int _count, int _beforeid){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Cart SET count = '"+ _count + "' WHERE id = '" + _beforeid + "'");
    }

    public void UpdatePrice(int _price, int _beforeid){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Cart SET price = '"+ _price + "' WHERE id = '" + _beforeid + "'");
    }

    // DELETE 문
    public void DeleteMenu(int _beforeid){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Cart WHERE id = '" + _beforeid + "'");
    }

    public void DeleteCart(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Cart");
    }

}
