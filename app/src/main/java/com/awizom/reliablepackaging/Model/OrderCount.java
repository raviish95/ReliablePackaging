package com.awizom.reliablepackaging.Model;

public class OrderCount {


    public int Totalstauscount ;
    public int Totalrunningstatus ;

    public int getTotalstauscount() {
        return Totalstauscount;
    }

    public void setTotalstauscount(int totalstauscount) {
        Totalstauscount = totalstauscount;
    }

    public int getTotalrunningstatus() {
        return Totalrunningstatus;
    }

    public void setTotalrunningstatus(int totalrunningstatus) {
        Totalrunningstatus = totalrunningstatus;
    }

    public int getTotalcompletedcount() {
        return Totalcompletedcount;
    }

    public void setTotalcompletedcount(int totalcompletedcount) {
        Totalcompletedcount = totalcompletedcount;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public int Totalcompletedcount;
    public int ClientId ;
}
