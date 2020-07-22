package com.example.defaultdemotoken.Model;

public class CurrentOrdersModel {

    String current_order_product_name,current_order_product_item_ID,current_order_product_price,current_order_product_status,order_id;

    public CurrentOrdersModel(String current_order_product_name, String current_order_product_item_ID, String current_order_product_price, String current_order_product_status, String order_id) {
        this.current_order_product_name = current_order_product_name;
        this.current_order_product_item_ID = current_order_product_item_ID;
        this.current_order_product_price = current_order_product_price;
        this.current_order_product_status = current_order_product_status;
        this.order_id = order_id;
    }

    public String getCurrent_order_product_name() {
        return current_order_product_name;
    }

    public void setCurrent_order_product_name(String current_order_product_name) {
        this.current_order_product_name = current_order_product_name;
    }

    public String getCurrent_order_product_item_ID() {
        return current_order_product_item_ID;
    }

    public void setCurrent_order_product_item_ID(String current_order_product_item_ID) {
        this.current_order_product_item_ID = current_order_product_item_ID;
    }

    public String getCurrent_order_product_price() {
        return current_order_product_price;
    }

    public void setCurrent_order_product_price(String current_order_product_price) {
        this.current_order_product_price = current_order_product_price;
    }

    public String getCurrent_order_product_status() {
        return current_order_product_status;
    }

    public void setCurrent_order_product_status(String current_order_product_status) {
        this.current_order_product_status = current_order_product_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
