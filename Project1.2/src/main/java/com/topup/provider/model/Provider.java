package com.topup.provider.model;

import java.util.Map;

public class Provider {

    private int code;
    private Double pulsa;
    private Double harga;
    private int terjual;

    public Provider(){}

    public Provider(Double pulsa, Double harga){
        this.pulsa = pulsa;
        this.harga = harga;
    }

    // Setter
    public void setPulsa(Double input){
        this.pulsa = input;
    }
    public void setHarga(Double input){
        this.harga = input;
    }
    public void setTerjual(int input){
        this.terjual=input;
    }

    public void tambahTerjual(){
        this.terjual++;
    }

    // Getter
    public int getCode(){
        return code;
    }
    public Double getPulsa(){
        return pulsa;
    }
    public Double getHarga(){
        return harga;
    }
    public int getTerjual(){
        return terjual;
    }

}
