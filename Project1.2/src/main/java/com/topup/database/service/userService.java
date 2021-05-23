package com.topup.database.service;

import com.google.gson.Gson;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.topup.database.model.message.Message;
import com.topup.database.model.order.Order;
import com.topup.provider.model.Provider;
import com.topup.database.model.user.User;
import com.topup.database.response.dbmessage;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class userService {

    private SqlSession session;
    private dbmessage send = new dbmessage();
    private int code_user;
    private String order_user;

    public void connectMyBatis() throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
    }

    // Input User ke Database
    public void inputUser(User user) throws IOException, TimeoutException {
        System.out.println("Memulai proses...");

        connectMyBatis();
        try{
            user.setVerifcode();
            user.getVerifcode();

            int hasil = session.insert("topupPulsa.insertUser", user);

            //String userCode = (String) session.selectOne("topupPulsa.getId", user);
            //user.setCode(Integer.parseInt(userCode));

            //session.insert("topupPulsa.insertLogin", user);

            //int hasil = session.insert("topupPulsa.insertFix", user);
            //int hasil = session.insert("topupPulsa.insertVerifCode", user);

            session.commit();

            if(hasil==1){
                System.out.println("Registrasi User berhasil");
                System.out.println("Mengirim kode verifikasi kepada user");

                Message message = new Message(user.getVerifcode());
                //Message message = new Message("Registrasi sudah berhasil");
                String input = new Gson().toJson(message);

                send.sendMessagetoRestController(input);
            } else {
                System.out.println("Registrasi User gagal");
                send.sendMessagetoRestController("Registrasi User gagal");
            }
        } catch(Exception e){
            //e.printStackTrace();
            System.out.println(" Sudah terdapat data");

            Message inputMessage = new Message("Sudah terdapat username yang sama");
            String input = new Gson().toJson(inputMessage);

            send.sendMessagetoRestController(input);
        }
    }

    public void verifNewUser(String input) throws IOException, TimeoutException {
        System.out.println("Memulai proses...");

        connectMyBatis();
        try{

            User user = new Gson().fromJson(input, User.class);
            user.getVerifcode();

            System.out.println("Kode Verifikasi dari RestAPI : " + user.getVerifcode());

            User verifiedUser = session.selectOne("topupPulsa.findRegisterByVerCode", user.getVerifcode());

            System.out.println(
                    verifiedUser.getCode() + "\n" +
                    verifiedUser.getName() + "\n " +
                    verifiedUser.getUsername() + "\n " +
                    verifiedUser.getEmail() + "\n " +
                    verifiedUser.getPhone() + "\n " +
                    verifiedUser.getStatus() + "\n " +
                    verifiedUser.getPassword() + "\n "
            );

            session.insert("topupPulsa.insertLogin", verifiedUser);

            session.insert("topupPulsa.insertFix", verifiedUser);

            verifiedUser.acceptStatusVerif();

            session.update("topupPulsa.updateStatusRegistrasi", verifiedUser);

            //int hasil = session.update("topupPulsa.updateStatusRegistrasi", verifiedUser);

            System.out.println("Verifikasi User berhasil");
            send.sendVerifyRespond("Verifikasi anda berhasil, anda sudah terdaftar");

            session.commit();
            /**
            if(hasil<0){
                System.out.println("Verifikasi User berhasil");
                //System.out.println("Mengirim kode verifikasi kepada user");

                //Message berhasilverif = new Message("Verifikasi anda berhasil, anda sudah terdaftar");

                //String inputMessage = new Gson().toJson(berhasilverif);

                send.sendVerifyRespond("Verifikasi anda berhasil, anda sudah terdaftar");

            } else {
                System.out.println("Verifikasi User gagal");

                //Message gagalverif = new Message("Verifikasi anda gagal");

                //String inputGagal = new Gson().toJson(gagalverif);

                send.sendVerifyRespond("Verifikasi anda gagal");
            }*/
        } catch(Exception e){
            e.printStackTrace();
            //System.out.println(" Sudah terdapat data");
            //String message = "Sudah terdapat data";

            //Message gagal = new Message("Verifikasi anda gagal");

            //String inputGagal = new Gson().toJson(gagal);

            send.sendVerifyRespond("Salah Kode Verifikasi, Silahkan Input kembali");
        }
    }

    public void checkUser(User input) throws IOException, TimeoutException {
        System.out.println("Memulai proses...");
        connectMyBatis();
        try{
            try{
                String username = input.getUsername();
                User checkUser = session.selectOne("topupPulsa.getUsernameLogin", username);

                if(input.getUsername().equals(checkUser.getUsername())){
                    if (input.getPassword().equals(checkUser.getPassword())) {
                        send.sendLoginRespond("Anda Berhasil Login dengan Username : " + input.getUsername());
                    } else {
                        send.sendLoginRespond("Password Salah");
                    }
                } else {
                    send.sendLoginRespond("Username tidak terdaftar, Silahkan Daftar");
                }
            } catch(Exception e){
                send.sendLoginRespond("Username tidak terdaftar, Silahkan Daftar");
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Input Orderan ke Database
    public void inputOrderFromUser(Order order) throws IOException, TimeoutException {
        System.out.println("Memulai proses...");

        connectMyBatis();

        try{
            int insertOrder = session.insert("topupPulsa.insertOrder", order);

            System.out.println(" [x] Insert Data untuk Order selesai");

            String code = (String) session.selectOne("topupPulsa.getOrderCode", order);
            order.setCode(Integer.parseInt(code));

            System.out.println(
                    "\nPemesanan Produk Oleh -->" +
                    "\nKode Username : " + order.getCode() +
                    "\nUsername : " + order.getUsername() +
                    "\nNomor Telepon : " + order.getPhone() +
                    "\nKode Produk : " + order.getProduk() +
                    "\nTotal Tagihan : " + order.getTagihan() +
                    "\nMetode Pembayaran : " + order.getMetode() +
                    "\nStatus Pembayaran : " + order.getStatus() + "\n"
            );

            this.code_user = order.getCode();

            session.commit();

            if(insertOrder==1){
                System.out.println("Registrasi Order selesai");
                send.sendOrderRespondtoRestController("Registrasi Order berhasil");
            } else {
                System.out.println("Registrasi Order gagal");
                send.sendOrderRespondtoRestController("Registrasi Order gagal");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void insertPriceToOrder(String message) throws IOException, TimeoutException {
        System.out.println("Memulai proses...");
        connectMyBatis();

        try{
            //Order order = (Order) session.selectOne("topupPulsa.getOrderCode", order_user);
            Order order = session.selectOne("topupPulsa.findByOrder", code_user);

            order.setTagihan(Double.parseDouble(message));

            int insertOrder = session.update("topupPulsa.updatePrice", order);

            String inputMessage = "\nPemesanan Produk Oleh -->" +
                    "\nKode Username : " + order.getCode() +
                    "\nUsername : " + order.getUsername() +
                    "\nNomor Telepon : " + order.getPhone() +
                    "\nTotal Tagihan : " + order.getTagihan() +
                    "\nMetode Pembayaran : " + order.getMetode() +
                    "\nStatus Pembayaran : " + order.getStatus() + "\n";

            System.out.println(" [x] Insert Data Tagihan untuk Kode Order : " + order.getCode() + " selesai");

            if(insertOrder==1){
                System.out.println("Registrasi Order selesai");
                send.sendOrderPricetoRestController(inputMessage);

                if(order.getMetode().equals("wallet")){
                    User user = new User();
                    String pembeli = order.getUsername();
                    user = session.selectOne("topupPulsa.findByUsername", pembeli);

                    order.setWallet(user.getWallet());

                    if (order.getWallet() > order.getTagihan()) {
                        order.hitungWallet();
                        user.setWallet(order.getWallet());

                        int updateUser = session.update("topupPulsa.updateWallet", user);

                        if(updateUser==1){
                            System.out.println("Pembayaran dengan E-Wallet Selesai");

                            order.setStatus("Lunas");

                            String paymentResult =
                                    "\nKode Order : " + order.getCode() +
                                    "\nUsername : " + order.getUsername() +
                                    "\nPhone : " + order.getPhone() +
                                    "\nMetode Pembayaran : " + order.getMetode() +
                                    "\nStatus Pembayaran : " + order.getStatus() +
                                    "\nSisa Wallet : " + user.getWallet() + "\n";

                            session.update("topupPulsa.updateStatus", order);

                            System.out.println(order.getProduk());

                            // Karena sudah lunas, maka kirim ke provider untuk memberi notifikasi

                            if (
                                    String.valueOf(order.getPhone()).substring(0,3).equals("821")
                            ) {
                                System.out.println("Mengirim Status sudah dibayar ke provider1");
                                send.sendStatusRespondToProvider(String.valueOf(order.getProduk()));
                            } else if (
                                    String.valueOf(order.getPhone()).substring(0,3).equals("822")
                            ) {
                                System.out.println("Mengirim Status sudah dibayar ke provider2");
                                send.sendStatusRespondToProvider2(String.valueOf(order.getProduk()));
                            }


                            String paymentResultJSON = new Gson().toJson(order);  // Mengirim dalam format JSON

                            //send.sendWalletPaymentDone(paymentResult);
                            send.sendWalletPaymentDone(paymentResultJSON);
                        } else {
                            System.out.println("Pembayaran dengan E-Wallet Gagal");

                            Message messageFailWallet = new Message("Pembayaran dengan E-Wallet Gagal");

                            String inputFail = new Gson().toJson(messageFailWallet);  // Change into JSON String
                            send.sendWalletPaymentDone(inputFail);
                        }
                    } else {
                        System.out.println("Wallet User : " + order.getUsername() + "tidak cukup");
                        Message notEnough = new Message("Wallet anda Tidak Cukup");
                        String inputNotEnough = new Gson().toJson(notEnough);
                        send.sendWalletPaymentDone(inputNotEnough);
                    }

                    //order.hitungWallet();
                    //user.setWallet(order.getWallet());
                    /**
                    int updateUser = session.update("topupPulsa.updateWallet", user);

                    if(updateUser==1){
                        System.out.println("Pembayaran dengan E-Wallet Selesai");

                        order.setStatus("Lunas");

                        String paymentResult =
                                "\nKode Order : " + order.getCode() +
                                "\nUsername : " + order.getUsername() +
                                "\nPhone : " + order.getPhone() +
                                "\nMetode Pembayaran : " + order.getMetode() +
                                "\nStatus Pembayaran : " + order.getStatus() +
                                "\nSisa Wallet : " + user.getWallet() + "\n";

                        session.update("topupPulsa.updateStatus", order);

                        System.out.println(order.getProduk());

                        // Karena sudah lunas, maka kirim ke provider untuk memberi notifikasi
                        send.sendStatusRespondToProvider(String.valueOf(order.getProduk()));

                        String paymentResultJSON = new Gson().toJson(order);  // Mengirim dalam format JSON

                        //send.sendWalletPaymentDone(paymentResult);
                        send.sendWalletPaymentDone(paymentResultJSON);
                    } else {
                        System.out.println("Pembayaran dengan E-Wallet Gagal");

                        Message messageFailWallet = new Message("Pembayaran dengan E-Wallet Gagal");

                        String inputFail = new Gson().toJson(messageFailWallet);  // Change into JSON String
                        send.sendWalletPaymentDone(inputFail);
                    }
                     */
                } else if (order.getMetode().equals("virtual account")) {
                    send.sendProductMethodToBank(String.valueOf(order.getPhone()));
                } else {
                    System.out.println("Pembayaran dengan E-Wallet Gagal");
                    Message messageNoMethod = new Message("Method tidak tersedia");

                    String inputNoMethod = new Gson().toJson(messageNoMethod);  // Change into JSON String
                    send.sendMethodErrorRespond(inputNoMethod);
                }
            } else {
                System.out.println("Registrasi Order gagal");
                Message messageFailOrder = new Message("Harga gagal dikirim");

                String inputFailOrder = new Gson().toJson(messageFailOrder);  // Change into JSON String
                send.sendOrderRespondtoRestController(inputFailOrder);
            }
            session.commit();
        } catch(Exception e){
            System.out.println("Error karena sudah terdapat data sejenis");
            send.sendOrderRespondtoRestController("Anda belum menyelesaikan pembayaran, mohon selesaikan pembayaran terlebih dahulu");
        }
    }
}
