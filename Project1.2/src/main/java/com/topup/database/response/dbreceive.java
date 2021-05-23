package com.topup.database.response;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.topup.database.model.message.Message;
import com.topup.database.model.order.Order;
import com.topup.database.model.user.User;
import com.topup.database.service.userService;
import com.topup.provider.model.Provider;
import com.topup.provider.response.send;
import com.topup.database.util.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class dbreceive {

    private ConnectionFactory connectionFactory;
    private Connection con;
    private Channel ch;
    private userService prService = new userService();
    private dbmessage messageToProvider = new dbmessage();
    private dbmessage messageToRestController = new dbmessage();
    private utility Util = new utility();

    public void connectRabbit() throws IOException, TimeoutException {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        con = connectionFactory.newConnection();
    }

    // Untuk Registrasi User dan update ke database
    public void inputUser(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("regisUser", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received " + message);
                try{
                    User user = new Gson().fromJson(message, User.class);
                    if (
                            (Util.checkUsernameRegex(user) == true) && (Util.checkPasswordRegex(user)==true)
                    ) {
                        prService.inputUser(user);
                    } else {
                        Message errorregex = new Message(
                                "Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase with 5 to 20 characters." +
                                        "\nPassword must contain at least one digit [0-9], at least one lowercase Latin character [a-z], at least one uppercase Latin character [A-Z], and at least one special character like ! @ # & ( )"
                        );
                        String inputMessage = new Gson().toJson(errorregex);
                        messageToRestController.sendMessagetoRestController(inputMessage);
                    }
                        //prService.inputUser(user);
                } catch(TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("regisUser", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void verifUser(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("verifUser", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received " + message);
                try{
                    //User user = new Gson().fromJson(message, User.class);
                    prService.verifNewUser(message);
                } catch(TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("verifUser", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loginUser(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("loginUser", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received " + message);

                try{
                    User user = new Gson().fromJson(message, User.class);
                    prService.checkUser(user);
                } catch(TimeoutException e){
                    e.printStackTrace();
                }

            };
            ch.basicConsume("loginUser", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // DB APP --> Provider
    // Menerima Pesan dari RestAPI lalu Request Data ke Provider
    public void askAllData(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("askProviderList", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received " + message);
                messageToProvider.sendMessagetoProvider(message);
            };
            ch.basicConsume("askProviderList", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void askAllData2(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("askProvider2List", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received " + message);
                messageToProvider.sendMessagetoProvider2(message);
            };
            ch.basicConsume("askProvider2List", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Menerima Pesan dari RestAPI lalu request data kode produk ke Provider
    public void messageBuyFromUser(){
        try{
            connectRabbit();

            ch = con.createChannel();
            ch.queueDeclare("inputOrderFromUser", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received message from RestAPI");

                Order order = new Gson().fromJson(message, Order.class);

                String sendToProvider = String.valueOf(order.getProduk());

                if(
                    String.valueOf(order.getPhone()).substring(0,3).equals("821")
                ) {
                    System.out.println("request harga produk dari Provider 1");

                    try {
                        prService.inputOrderFromUser(order);
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }

                    messageToProvider.sendAdmitRequestToProvider(sendToProvider);  // Ke Provider 1
                    messageToRestController.sendErrorProviderCode("provider tersedia");

                } else if (
                    String.valueOf(order.getPhone()).substring(0,3).equals("822")
                ) {
                    System.out.println("request harga produk dari Provider 2");

                    try {
                        prService.inputOrderFromUser(order);
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }

                    messageToProvider.sendAdmitRequestToProvider2(sendToProvider); // Ke Provider 2
                    messageToRestController.sendErrorProviderCode("provider tersedia");

                } else {
                    System.out.println("Provider untuk nomor telepon : " + order.getPhone() + " tidak tersedia");
                    messageToRestController.sendErrorProviderCode("Provider untuk nomor telepon : " + order.getPhone() + " tidak tersedia");
                }
            };

            ch.basicConsume("inputOrderFromUser", true, deliverCallback, consumerTag -> { });

        } catch (Exception e){
            String message = "Anda belum menyelesaikan pembayaran. Mohon selesaikan pembayaran";
            messageToRestController.sendErrorOrder(message);
        }
    }

    // Provider --> DB APP
    // Mendapatkan Data dari Provider ke Database
    public void ListProductFromProviderToDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("ListFromProvider", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received message from provider ");



                messageToRestController.ListProductToRestController(message);

            };
            ch.basicConsume("ListFromProvider", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ListProductFromProvider2ToDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("ListFromProvider2", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received message from provider ");

                messageToRestController.ListProduct2ToRestController(message);

            };
            ch.basicConsume("ListFromProvider2", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Mendapatkan Harga dari Provider
    public void messagePriceFromProvidertoDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("SendCodePriceFromProviderToDB", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received price data from provider ");

                try {
                    // Insert Price Data to DB and send to DB
                    prService.insertPriceToOrder(message);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            };
            ch.basicConsume("SendCodePriceFromProviderToDB", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void messagePriceFromProvider2toDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("SendCodePriceFromProvider2ToDB", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received price data from provider ");

                try {
                    // Insert Price Data to DB and send to DB
                    prService.insertPriceToOrder(message);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            };
            ch.basicConsume("SendCodePriceFromProvider2ToDB", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Bank --> DB APP
    // Mendapatkan Data Kode Transfer Virtual Account ke Database
    public void messageTransferCodeFromBankToDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("SendTransfercodeToDB", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received price data from provider ");

                System.out.println("Send Transfer Code : " + message);
                messageToProvider.sendTransferCodeToRestController(message);

            };
            ch.basicConsume("SendTransfercodeToDB", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
