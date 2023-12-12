package com.example.afinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OrderDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "history.db";

    public OrderDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성이 될 때 호출

        // 데이터베이스 -> 테이블 -> 컬럼 -> 값

        // 데이터베이스 생성. IF NOT EXISTS : 테이블이 이미 존재하면 실행하지 않음
        db.execSQL("CREATE TABLE History (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, count INTEAGER NOT NULL, price INTEGER NOT NULL)");
//        DeleteOrder();      // 첫 실행때 주문내역 DB 테이블 데이터 제거. 안됨;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS History");
        }
    }

    // SELECT 문
    public ArrayList<OrderData> getMenuList(){
        ArrayList<OrderData> orderDatas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM History ORDER BY name ASC", null);
        if(cursor.getCount() != 0){
            // 데이터가 있을 때. 조회 가능
            while (cursor.moveToNext()){        // 데이터베이스 값들을 다음 값이 없을때까지 반복

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

                OrderData orderData = new OrderData();
                orderData.setId(id);
                orderData.setOrder_name(name);
                orderData.setOrder_count(count);
                orderData.setOrder_price(price);
                orderDatas.add(orderData);
            }
        }
        cursor.close();

        return orderDatas;
    }

    // INSERT 문
    public void InsertOrder(String _name, int _count, int _price){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO History (name, count, price) VALUES ('" + _name + "', '" + _count + "','" + _price + "');");
    }

    // UPDATE 문
    public void UpdateCart(String _name, int _price, String _explan, String _beforename){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Cart SET name = '"+ _name + "', price = '"+ _price + "', explan = '"+ _explan + "' WHERE name = '" + _beforename + "'");
    }

    // DELETE 문
    public void DeleteOrder(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM History");
    }

    public void DeleteMenu(int _beforeid){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM History WHERE id = '" + _beforeid + "'");
    }
}
