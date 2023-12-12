package com.example.afinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MenuDBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static final String DB_NAME = "menu.db";

    public MenuDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성이 될 때 호출

        // 데이터베이스 -> 테이블 -> 컬럼 -> 값

        // 첫 실행(onCreate)시 테이블이 존재하면 삭제
//        db.execSQL("DROP TABLE IF EXISTS Cart");

        // 데이터베이스 생성. IF NOT EXISTS : 테이블이 이미 존재하면 실행하지 않음
        db.execSQL("CREATE TABLE Menu (id INTEGER PRIMARY KEY AUTOINCREMENT, image INTEGER NOT NULL, name TEXT NOT NULL, kind TEXT NOT NULL,  detail TEXT NOT NULL,price INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS Menu");
//            onCreate(db);
        }
    }

    // SELECT 문
    public ArrayList<MenuData> getMenuList(){
        ArrayList<MenuData> menuDatas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Menu", null);
        if(cursor.getCount() != 0){
            // 데이터가 있을 때. 조회 가능
            while (cursor.moveToNext()){        // 데이터베이스 값들을 다음 값이 없을때까지 반복

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow("image"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String kind = cursor.getString(cursor.getColumnIndexOrThrow("kind"));
                String detail = cursor.getString(cursor.getColumnIndexOrThrow("detail"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

                MenuData menuData = new MenuData();
                menuData.setId(id);
                menuData.setMenu_image(image);
                menuData.setMenu_kind(kind);
                menuData.setMenu_name(name);
                menuData.setMenu_detail(detail);
                menuData.setMenu_price(price);
                menuDatas.add(menuData);
            }
        }
        cursor.close();

        return menuDatas;
    }

    // INSERT 문
    public void InsertMenu(int _image, String _name, String _kind, int _price, String _detail){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Menu (image, name, kind, price, detail) VALUES ('" + _image + "', '" + _name + "', '" + _kind + "','"  + _price + "','"  + _detail + "');");
    }

    // UPDATE 문
    public void UpdateMenu(String _name, int _price, String _explan, String _beforename){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Menu SET name = '"+ _name + "', price = '"+ _price + "', explan = '"+ _explan + "' WHERE name = '" + _beforename + "'");
    }

    // DELETE 문
    public void DeleteMenu(int _beforeid){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Menu WHERE id = '" + _beforeid + "'");
    }

    public void DeleteMenu(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Menu");
    }

}
