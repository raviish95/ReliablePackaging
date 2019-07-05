package com.awizom.reliablepackaging.Model;

public class TrackingStatus {

    public int Printstatus ;
    public int CylinderStatus ;
    public int InspectionStatus ;

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
