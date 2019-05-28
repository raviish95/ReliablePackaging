package com.awizom.reliablepackaging.Model;

public class MyProfileView {
    public int ClientID ;
    public String Name ;

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBillingAdddress() {
        return BillingAdddress;
    }

    public void setBillingAdddress(String billingAdddress) {
        BillingAdddress = billingAdddress;
    }

    public int getPinCode() {
        return PinCode;
    }

    public void setPinCode(int pinCode) {
        PinCode = pinCode;
    }

    public String ContactName;
    public String PhoneNumber ;
    public String Email ;
    public String BillingAdddress ;
    public int PinCode;
}
