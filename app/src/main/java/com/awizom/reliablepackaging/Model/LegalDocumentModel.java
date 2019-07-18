package com.awizom.reliablepackaging.Model;

public class LegalDocumentModel {

    public int DocumentId;
    public int ClientID;

    public int getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(int documentId) {
        DocumentId = documentId;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getUploadedDate() {
        return UploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        UploadedDate = uploadedDate;
    }

    public String getUploadedBy() {
        return UploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        UploadedBy = uploadedBy;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String UploadedDate;
    public String UploadedBy ;
    public String URL ;
}
