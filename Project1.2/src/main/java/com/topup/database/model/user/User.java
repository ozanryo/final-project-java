package com.topup.database.model.user;

import com.topup.database.model.order.Order;

import java.util.Map;
import java.util.Random;

public class User {
    private int code;
    private String name;
    private int phone;
    private String address;
    private String email;
    private String password;
    private String username;
    public String verifcode = null;
    public String statusVerif = "belum verifikasi";

    private Double wallet = 0.0;
    private String metode = "belum dipilih";
    private String status = "belum diterima Provider";

    public User(){}

    public User(String inputName, int inputPhone, String inputAddress,
                String inputEmail, String inputUser, String inputPass){
        this.name = inputName;
        this.phone = inputPhone;
        this.address = inputAddress;
        this.email = inputEmail;
        this.password = inputPass;
        this.username = inputUser;
    }

    // Setter
    public void setCode(int input){
        this.code=input;
    }
    public void setName(String input){
        this.name = input;
    }
    public void setPhone(int input){
        this.phone = input;
    }
    public void setEmail(String input){
        this.email = input;
    }
    public void setAddress(String input){
        this.address = input;
    }
    public void setPassword(String input){
        this.password = input;
    }
    public void setUsername(String input){
        this.username = input;
    }
    public void setWallet(Double input){
        this.wallet = input;
    }
    public void setMetode(String input){
        this.metode = input;
    }
    public void setStatus(String input){
        this.status = input;
    }
    public void setVerifcode(){
        Random random = new Random();
        int resRandom = random.nextInt((9999 - 100) + 1) + 10;
        this.verifcode = String.valueOf(resRandom);
    }
    public void acceptStatusVerif(){
        this.statusVerif="registrasi selesai";
    }

    // Getter
    public int getCode(){
        return code;
    }
    public String getName(){
        return name;
    }
    public int getPhone(){
        return phone;
    }
    public String getAddress(){
        return address;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return username;
    }
    public Double getWallet(){
        return wallet;
    }
    public String getMetode(){
        return metode;
    }
    public String getStatus(){
        return status;
    }
    public String getStatusVerif(){
        return statusVerif;
    }
    public String getVerifcode(){
        return verifcode;
    }

    // Calculate
    public void hitungWallet(Double inputWallet, Double inputHarga){
        if(inputWallet > inputHarga){
            this.wallet = inputWallet - inputHarga;
        } else {
            this.wallet = inputWallet;
            System.out.println("Wallet tidak cukup");
        }
    }
}
