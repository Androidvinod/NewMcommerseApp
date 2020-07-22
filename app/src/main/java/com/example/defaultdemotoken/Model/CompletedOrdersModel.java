package com.example.defaultdemotoken.Model;

public class CompletedOrdersModel {
    String complete_order_product_name,complete_order_product_item_ID,complete_order_product_price,complete_order_product_status,order_idd;

    public CompletedOrdersModel(String complete_order_product_name, String complete_order_product_item_ID, String complete_order_product_price, String complete_order_product_status, String order_idd) {
        this.complete_order_product_name = complete_order_product_name;
        this.complete_order_product_item_ID = complete_order_product_item_ID;
        this.complete_order_product_price = complete_order_product_price;
        this.complete_order_product_status = complete_order_product_status;
        this.order_idd = order_idd;
    }

    public String getComplete_order_product_name() {
        return complete_order_product_name;
    }

    public void setComplete_order_product_name(String complete_order_product_name) {
        this.complete_order_product_name = complete_order_product_name;
    }

    public String getComplete_order_product_item_ID() {
        return complete_order_product_item_ID;
    }

    public void setComplete_order_product_item_ID(String complete_order_product_item_ID) {
        this.complete_order_product_item_ID = complete_order_product_item_ID;
    }

    public String getComplete_order_product_price() {
        return complete_order_product_price;
    }

    public void setComplete_order_product_price(String complete_order_product_price) {
        this.complete_order_product_price = complete_order_product_price;
    }

    public String getComplete_order_product_status() {
        return complete_order_product_status;
    }

    public void setComplete_order_product_status(String complete_order_product_status) {
        this.complete_order_product_status = complete_order_product_status;
    }

    public String getOrder_idd() {
        return order_idd;
    }

    public void setOrder_idd(String order_idd) {
        this.order_idd = order_idd;
    }
}
