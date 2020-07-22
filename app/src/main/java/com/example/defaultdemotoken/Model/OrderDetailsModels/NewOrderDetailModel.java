
package com.example.defaultdemotoken.Model.OrderDetailsModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOrderDetailModel {

    @SerializedName("base_currency_code")
    @Expose
    private String baseCurrencyCode;
    @SerializedName("base_discount_amount")
    @Expose
    private Integer baseDiscountAmount;
    @SerializedName("base_grand_total")
    @Expose
    private Integer baseGrandTotal;
    @SerializedName("base_shipping_amount")
    @Expose
    private Integer baseShippingAmount;
    @SerializedName("base_subtotal")
    @Expose
    private Integer baseSubtotal;
    @SerializedName("base_tax_amount")
    @Expose
    private Integer baseTaxAmount;
    @SerializedName("base_total_due")
    @Expose
    private Integer baseTotalDue;
    @SerializedName("billing_address_id")
    @Expose
    private Integer billingAddressId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_firstname")
    @Expose
    private String customerFirstname;
    @SerializedName("customer_group_id")
    @Expose
    private Integer customerGroupId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_lastname")
    @Expose
    private String customerLastname;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("entity_id")
    @Expose
    private Integer entityId;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("increment_id")
    @Expose
    private String incrementId;
    @SerializedName("protect_code")
    @Expose
    private String protectCode;
    @SerializedName("shipping_amount")
    @Expose
    private Integer shippingAmount;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("subtotal_incl_tax")
    @Expose
    private Integer subtotalInclTax;
    @SerializedName("tax_amount")
    @Expose
    private Integer taxAmount;
    @SerializedName("total_due")
    @Expose
    private Integer totalDue;
    @SerializedName("total_item_count")
    @Expose
    private Integer totalItemCount;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("billing_address")
    @Expose
    private BillingAddress billingAddress;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("status_histories")
    @Expose
    private List<Object> statusHistories = null;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public Integer getBaseDiscountAmount() {
        return baseDiscountAmount;
    }

    public void setBaseDiscountAmount(Integer baseDiscountAmount) {
        this.baseDiscountAmount = baseDiscountAmount;
    }

    public Integer getBaseGrandTotal() {
        return baseGrandTotal;
    }

    public void setBaseGrandTotal(Integer baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    public Integer getBaseShippingAmount() {
        return baseShippingAmount;
    }

    public void setBaseShippingAmount(Integer baseShippingAmount) {
        this.baseShippingAmount = baseShippingAmount;
    }

    public Integer getBaseSubtotal() {
        return baseSubtotal;
    }

    public void setBaseSubtotal(Integer baseSubtotal) {
        this.baseSubtotal = baseSubtotal;
    }

    public Integer getBaseTaxAmount() {
        return baseTaxAmount;
    }

    public void setBaseTaxAmount(Integer baseTaxAmount) {
        this.baseTaxAmount = baseTaxAmount;
    }

    public Integer getBaseTotalDue() {
        return baseTotalDue;
    }

    public void setBaseTotalDue(Integer baseTotalDue) {
        this.baseTotalDue = baseTotalDue;
    }

    public Integer getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Integer billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public Integer getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Integer customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getIncrementId() {
        return incrementId;
    }

    public void setIncrementId(String incrementId) {
        this.incrementId = incrementId;
    }

    public String getProtectCode() {
        return protectCode;
    }

    public void setProtectCode(String protectCode) {
        this.protectCode = protectCode;
    }

    public Integer getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Integer shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getSubtotalInclTax() {
        return subtotalInclTax;
    }

    public void setSubtotalInclTax(Integer subtotalInclTax) {
        this.subtotalInclTax = subtotalInclTax;
    }

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Integer totalDue) {
        this.totalDue = totalDue;
    }

    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Object> getStatusHistories() {
        return statusHistories;
    }

    public void setStatusHistories(List<Object> statusHistories) {
        this.statusHistories = statusHistories;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
