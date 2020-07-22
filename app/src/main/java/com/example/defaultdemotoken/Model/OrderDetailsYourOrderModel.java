package com.example.defaultdemotoken.Model;

public class OrderDetailsYourOrderModel {

    String prod_name,prod_item_id,prod_price;

    public OrderDetailsYourOrderModel(String prod_name, String prod_item_id, String prod_price) {
        this.prod_name = prod_name;
        this.prod_item_id = prod_item_id;
        this.prod_price = prod_price;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_item_id() {
        return prod_item_id;
    }

    public void setProd_item_id(String prod_item_id) {
        this.prod_item_id = prod_item_id;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }
}
