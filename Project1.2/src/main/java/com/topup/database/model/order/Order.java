package com.topup.database.model.order;

public class Order {
    private int code;
    private String username;
    private int phone;
    private int produk = 0;
    private String provider;
    private Double tagihan = 0.0;
    private String metode;
    private String status = "belum bayar";
    private Double wallet;

    // Constructor
    public Order(){

    }

    public Order(String inputUser, int inputPhone, int inputProduk, Double inputTagihan,
                 String inputMetode, String inputStatus) {
        this.username = inputUser;
        this.phone = inputPhone;
        this.produk = inputProduk;
        this.tagihan = inputTagihan;
        this.metode = inputMetode;
        this.status = inputStatus;
    }

    // Setter
    public void setCode(int input) {
        this.code = input;
    }
    public void getUsernmae(String input){
        this.username = input;
    }
    public void setMetode(String input){
        this.metode = input;
    }
    public void setPhone(int input) {
        this.phone = input;
    }
    public void setCodeProduk(int input) {
        this.produk = input;
    }
    public void setTagihan(Double input){
        this.tagihan = input;
    }
    public void setStatus(String input){
        this.status = input;
    }
    public void setWallet(Double input){
        this.wallet = input;
    }
    public void setProvider(String input){
        this.provider = input;
    }

    // Getter
    public int getCode(){
        return code;
    }
    public String getUsername(){
        return username;
    }
    public String getMetode(){
        return metode;
    }
    public int getPhone(){
        return phone;
    }
    public int getProduk(){
        return produk;
    }
    public Double getTagihan(){
        return tagihan;
    }
    public String getStatus(){
        return status;
    }
    public Double getWallet(){
        return wallet;
    }
    public String getProvider(){
        return provider;
    }

    // Calculate
    public void hitungWallet(){
        if(getWallet() > getTagihan()) {
            this.wallet = getWallet() - getTagihan();
        } else {
            System.out.println("Jumlah Wallet tidak cukup");
        }
    }

}
