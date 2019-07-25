package com.awizom.reliablepackaging.Model;

public class MyProfileView {
    public int ClientID;
    public String Name ;
    public String ContactName;
    public String PhoneNumber ;
    public String AditionalPhoneNumber ;



    public String Email ;
    public String BillingAdddress;
    public String PAN ;
    public String Gstin;
    public String Tin;
    public String AccountType;

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String CompanyName;
    public int PinCode ;

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
    public String getAditionalPhoneNumber() {
        return AditionalPhoneNumber;
    }

    public void setAditionalPhoneNumber(String aditionalPhoneNumber) {
        AditionalPhoneNumber = aditionalPhoneNumber;
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

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getGstin() {
        return Gstin;
    }

    public void setGstin(String gstin) {
        Gstin = gstin;
    }

    public String getTin() {
        return Tin;
    }

    public void setTin(String tin) {
        Tin = tin;
    }

    public int getPinCode() {
        return PinCode;
    }

    public void setPinCode(int pinCode) {
        PinCode = pinCode;
    }
}
