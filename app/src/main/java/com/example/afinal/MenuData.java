package com.example.afinal;

public class MenuData {

    private int id, menu_image, menu_price;
    private String menu_name, menu_kind, menu_detail;

    public MenuData(){
    }

    public int getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(int menu_image) {
        this.menu_image = menu_image;
    }

    public int getMenu_price() {
        return menu_price;
    }

    public String getMenu_detail() {
        return menu_detail;
    }

    public void setMenu_detail(String menu_detail) {
        this.menu_detail = menu_detail;
    }

    public void setMenu_price(int menu_price) {
        this.menu_price = menu_price;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_kind() {
        return menu_kind;
    }

    public void setMenu_kind(String menu_kind) {
        this.menu_kind = menu_kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
