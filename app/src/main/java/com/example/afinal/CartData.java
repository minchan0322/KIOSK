package com.example.afinal;

public class CartData {

    private int id, cart_image, cart_price, cart_count;
    private String cart_name, cart_option;

    public CartData(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCart_image() {
        return cart_image;
    }

    public void setCart_image(int cart_image) {
        this.cart_image = cart_image;
    }

    public int getCart_price() {
        return cart_price;
    }

    public void setCart_price(int cart_price) {
        this.cart_price = cart_price;
    }

    public int getCart_count() {
        return cart_count;
    }

    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }

    public String getCart_name() {
        return cart_name;
    }

    public void setCart_name(String cart_name) {
        this.cart_name = cart_name;
    }

    public String getCart_option() {
        return cart_option;
    }

    public void setCart_option(String cart_option) {
        this.cart_option = cart_option;
    }
}
