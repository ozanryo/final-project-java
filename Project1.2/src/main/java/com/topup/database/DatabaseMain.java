package com.topup.database;

import com.topup.database.response.dbreceive;

public class DatabaseMain {

    private static dbreceive receive = new dbreceive();

    public static void main(String[] args){
        System.out.println(" [x] Waiting for message ");
        try{

            receive.inputUser(); // Menerima pesan dari restapicontroller
            receive.loginUser();  // Menerima pesan dari Rest Controller untuk Login
            receive.verifUser();  // Menerima pesan untuk verifikasi dari Rest Controller
            receive.askAllData();  // Menerima pesan dari rest api controller dan mengirim list produk
            receive.askAllData2();  // Menerima pesan dari rest api controller dan mengirim list produk2
            receive.ListProductFromProviderToDB(); // Menerima Pesan List Produk Provider
            receive.ListProductFromProvider2ToDB();  // Menerima Pesan List Produk Provider2
            receive.messageBuyFromUser(); // Menerima pesan dari res api controller
            receive.messagePriceFromProvidertoDB(); // Menerima pesan dari provider berupa harga
            receive.messagePriceFromProvider2toDB(); // Menerima pesan dari provider2 berupa harga
            receive.messageTransferCodeFromBankToDB(); // Menerima Pesan Kode Transfer ke Rest Controller

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
