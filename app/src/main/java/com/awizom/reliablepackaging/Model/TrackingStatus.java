package com.awizom.reliablepackaging.Model;

public class TrackingStatus {

    public int Printstatus ;
    public int CylinderStatus ;
    public int InspectionStatus ;
    public String PrintDate;
    public String CylinderReciveDate ;
    public String InspectionDate ;
    public String LaminationDate ;
    public String SlittingDate;

    public String getPrintDate() {
        return PrintDate;
    }

    public void setPrintDate(String printDate) {
        PrintDate = printDate;
    }

    public String getCylinderReciveDate() {
        return CylinderReciveDate;
    }

    public void setCylinderReciveDate(String cylinderReciveDate) {
        CylinderReciveDate = cylinderReciveDate;
    }

    public String getInspectionDate() {
        return InspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        InspectionDate = inspectionDate;
    }

    public String getLaminationDate() {
        return LaminationDate;
    }

    public void setLaminationDate(String laminationDate) {
        LaminationDate = laminationDate;
    }

    public String getSlittingDate() {
        return SlittingDate;
    }

    public void setSlittingDate(String slittingDate) {
        SlittingDate = slittingDate;
    }

    public String getPouchingDate() {
        return PouchingDate;
    }

    public void setPouchingDate(String pouchingDate) {
        PouchingDate = pouchingDate;
    }

    public String getDispatchedDate() {
        return DispatchedDate;
    }

    public void setDispatchedDate(String dispatchedDate) {
        DispatchedDate = dispatchedDate;
    }

    public String PouchingDate ;
    public String DispatchedDate;
    public int getPrintstatus() {
        return Printstatus;
    }

    public void setPrintstatus(int printstatus) {
        Printstatus = printstatus;
    }

    public int getCylinderStatus() {
        return CylinderStatus;
    }

    public void setCylinderStatus(int cylinderStatus) {
        CylinderStatus = cylinderStatus;
    }

    public int getInspectionStatus() {
        return InspectionStatus;
    }

    public void setInspectionStatus(int inspectionStatus) {
        InspectionStatus = inspectionStatus;
    }

    public int getLaminationStatus() {
        return LaminationStatus;
    }

    public void setLaminationStatus(int laminationStatus) {
        LaminationStatus = laminationStatus;
    }

    public int getSlittingstatus() {
        return Slittingstatus;
    }

    public void setSlittingstatus(int slittingstatus) {
        Slittingstatus = slittingstatus;
    }

    public int getPouchingStatus() {
        return PouchingStatus;
    }

    public void setPouchingStatus(int pouchingStatus) {
        PouchingStatus = pouchingStatus;
    }

    public int getDispatchStatus() {
        return DispatchStatus;
    }

    public void setDispatchStatus(int dispatchStatus) {
        DispatchStatus = dispatchStatus;
    }

    public int LaminationStatus ;
    public int Slittingstatus ;
    public int PouchingStatus ;
    public int DispatchStatus ;
}
