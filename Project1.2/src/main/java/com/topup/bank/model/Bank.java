package com.topup.bank.model;

public class Bank {
    private int code;
    private String transfer_code;
    private String status = "belum lunas";

    // Constructor
    public Bank(){}
    public Bank(String transfer_code, String status){
        this.transfer_code = transfer_code;
        this.status = status;
    }

    // Setter
    public void setTransferCode(String input){
        this.transfer_code = input;
    }
    public void setStatus(String input){
        this.status = input;
    }

    // Getter
    public String getTransferCode(){
        return transfer_code;
    }
    public String getStatus(){
        return status;
    }
}
