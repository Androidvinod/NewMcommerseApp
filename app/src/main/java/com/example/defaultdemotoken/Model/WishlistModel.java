package com.example.defaultdemotoken.Model;

public class WishlistModel {

        String wishlist_item_id,wishlist_id,product_id,sku,price,special_price,name,thumbnail;

    public WishlistModel(String wishlist_item_id, String wishlist_id, String product_id,
                         String sku, String price, String special_price, String name, String thumbnail) {
        this.wishlist_item_id = wishlist_item_id;
        this.wishlist_id = wishlist_id;
        this.product_id = product_id;
        this.sku = sku;
        this.price = price;
        this.special_price = special_price;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getWishlist_item_id() {
        return wishlist_item_id;
    }

    public void setWishlist_item_id(String wishlist_item_id) {
        this.wishlist_item_id = wishlist_item_id;
    }

    public String getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(String wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
