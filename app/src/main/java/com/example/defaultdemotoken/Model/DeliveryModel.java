package com.example.defaultdemotoken.Model;

public class DeliveryModel {

    String carrier_code,method_code,carrier_title,method_title,amount,base_amount,available,error_message,price_excl_tax,price_incl_tax;

    public DeliveryModel(String carrier_code, String method_code, String carrier_title, String method_title, String amount, String base_amount, String available, String error_message, String price_excl_tax, String price_incl_tax) {
        this.carrier_code = carrier_code;
        this.method_code = method_code;
        this.carrier_title = carrier_title;
        this.method_title = method_title;
        this.amount = amount;
        this.base_amount = base_amount;
        this.available = available;
        this.error_message = error_message;
        this.price_excl_tax = price_excl_tax;
        this.price_incl_tax = price_incl_tax;
    }

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

    public String getMethod_code() {
        return method_code;
    }

    public void setMethod_code(String method_code) {
        this.method_code = method_code;
    }

    public String getCarrier_title() {
        return carrier_title;
    }

    public void setCarrier_title(String carrier_title) {
        this.carrier_title = carrier_title;
    }

    public String getMethod_title() {
        return method_title;
    }

    public void setMethod_title(String method_title) {
        this.method_title = method_title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBase_amount() {
        return base_amount;
    }

    public void setBase_amount(String base_amount) {
        this.base_amount = base_amount;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getPrice_excl_tax() {
        return price_excl_tax;
    }

    public void setPrice_excl_tax(String price_excl_tax) {
        this.price_excl_tax = price_excl_tax;
    }

    public String getPrice_incl_tax() {
        return price_incl_tax;
    }

    public void setPrice_incl_tax(String price_incl_tax) {
        this.price_incl_tax = price_incl_tax;
    }
}
