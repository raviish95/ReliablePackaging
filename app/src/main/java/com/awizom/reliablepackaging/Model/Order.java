package com.awizom.reliablepackaging.Model;

public class Order {

    public int OrderId;
    public String ProductName;
    public int TotalItem;
    public double Weight;
    public double TotalAmount;
    public double Discount;
    public double UnitPrice;
    public int TotalProduct;
    public int ClientID;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getTotalItem() {
        return TotalItem;
    }

    public void setTotalItem(int totalItem) {
        TotalItem = totalItem;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getTotalProduct() {
        return TotalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        TotalProduct = totalProduct;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }


}
