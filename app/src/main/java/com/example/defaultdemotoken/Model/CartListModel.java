
package com.example.defaultdemotoken.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartListModel {

    /*@SerializedName("item_id")
    @Expose*/
    private String itemId;
    /*@SerializedName("sku")
    @Expose*/
    private String sku;
    /*@SerializedName("qty")
    @Expose*/
    private String qty;
    /*@SerializedName("name")
    @Expose*/
    private String name;
    /*@SerializedName("price")
    @Expose*/
    private Integer price;
    /*@SerializedName("product_type")
    @Expose*/
    private String productType;
    /*@SerializedName("quote_id")
    @Expose*/
    private String quoteId;
    /*@SerializedName("extension_attributes")
    @Expose*/
    //private ExtensionAttributes extensionAttributes;
    private String image;

    /*public CartListModel(String itemId, String sku, String qty, String name, Integer price, String productType, String quoteId) {
        this.itemId = itemId;
        this.sku = sku;
        this.qty = qty;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.quoteId = quoteId;
    }*/

    public CartListModel(String itemId, String sku, String qty, String name, Integer price, String productType, String quoteId, String image) {
        this.itemId = itemId;
        this.sku = sku;
        this.qty = qty;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.quoteId = quoteId;
        this.image = image;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /*public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }*/

    /*public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }*/

}
