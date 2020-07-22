package com.example.defaultdemotoken.Model.HomeModel;

public class BestSelling {
    private String wishlist_item_id;
    private String isWishlisted;
    String name,image,sku,id,rating,price;

    public BestSelling(String name, String image, String sku, String id, String rating, String price,String isWishlisted, String wishlist_item_id) {
        this.name = name;
        this.image = image;
        this.sku = sku;
        this.id = id;
        this.rating = rating;
        this.price = price;
        this.isWishlisted = isWishlisted;
        this.wishlist_item_id = wishlist_item_id;
    }



    public String getIsWishlisted() {
        return isWishlisted;
    }

    public void setIsWishlisted(String isWishlisted) {
        this.isWishlisted = isWishlisted;
    }

    public String getWishlist_item_id() {
        return wishlist_item_id;
    }

    public void setWishlist_item_id(String wishlist_item_id) {
        this.wishlist_item_id = wishlist_item_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
