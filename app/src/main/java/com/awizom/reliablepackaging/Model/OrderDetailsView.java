package com.awizom.reliablepackaging.Model;

public class OrderDetailsView {
    public int OrderId ;
    public String ImageUrl ;
    public String ProductName;
    public int TotalItem ;
    public double Weight ;
    public double TotalAmount ;
    public double Discount;
    public double UnitPrice;
    public int TotalProduct ;
    public int ClientID;
    public String CreatedDate ;
    public String VerifiedBy ;
    public String VerifiedDate;
    public String TINNO ;
    public String GSTNO ;
    public double CGST ;
    public double SGST;
    public double IGST ;
    public double UTGST ;
    public String OrderNo;
    public String OrderType;

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String JobName;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

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

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getVerifiedBy() {
        return VerifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        VerifiedBy = verifiedBy;
    }

    public String getVerifiedDate() {
        return VerifiedDate;
    }

    public void setVerifiedDate(String verifiedDate) {
        VerifiedDate = verifiedDate;
    }

    public String getTINNO() {
        return TINNO;
    }

    public void setTINNO(String TINNO) {
        this.TINNO = TINNO;
    }

    public String getGSTNO() {
        return GSTNO;
    }

    public void setGSTNO(String GSTNO) {
        this.GSTNO = GSTNO;
    }

    public double getCGST() {
        return CGST;
    }

    public void setCGST(double CGST) {
        this.CGST = CGST;
    }

    public double getSGST() {
        return SGST;
    }

    public void setSGST(double SGST) {
        this.SGST = SGST;
    }

    public double getIGST() {
        return IGST;
    }

    public void setIGST(double IGST) {
        this.IGST = IGST;
    }

    public double getUTGST() {
        return UTGST;
    }

    public void setUTGST(double UTGST) {
        this.UTGST = UTGST;
    }


}
