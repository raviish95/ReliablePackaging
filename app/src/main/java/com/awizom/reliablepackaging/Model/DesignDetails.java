package com.awizom.reliablepackaging.Model;

public class DesignDetails {

    public int DesignId ;

    public int getDesignId() {
        return DesignId;
    }

    public void setDesignId(int designId) {
        DesignId = designId;
    }

    public String getDesignName() {
        return DesignName;
    }

    public void setDesignName(String designName) {
        DesignName = designName;
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

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String DesignName ;
    public String VerifiedBy ;
    public String VerifiedDate;
    public int OrderID ;
    public String ImageUrl ;
    public boolean Active ;

}
