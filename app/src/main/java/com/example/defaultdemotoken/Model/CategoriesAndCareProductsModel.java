package com.example.defaultdemotoken.Model;

public class CategoriesAndCareProductsModel {
    String cnc_prod_id,cnc_prod_image,cnc_prod_name;

    public CategoriesAndCareProductsModel(String cnc_prod_id, String cnc_prod_image, String cnc_prod_name) {
        this.cnc_prod_id = cnc_prod_id;
        this.cnc_prod_image = cnc_prod_image;
        this.cnc_prod_name = cnc_prod_name;
    }

    public String getCnc_prod_id() {
        return cnc_prod_id;
    }

    public void setCnc_prod_id(String cnc_prod_id) {
        this.cnc_prod_id = cnc_prod_id;
    }

    public String getCnc_prod_image() {
        return cnc_prod_image;
    }

    public void setCnc_prod_image(String cnc_prod_image) {
        this.cnc_prod_image = cnc_prod_image;
    }

    public String getCnc_prod_name() {
        return cnc_prod_name;
    }

    public void setCnc_prod_name(String cnc_prod_name) {
        this.cnc_prod_name = cnc_prod_name;
    }
}
