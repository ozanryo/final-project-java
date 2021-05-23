package com.topup.restapi.response;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeoutException;

public class controllerReceive {

    private String message;

    // Receive Message From DB
    public String receiveMessageFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("messageFromDB", false, false, false, null);
        //System.out.println(" [*] Waiting for messages from database");

        /**
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("messageFromDB", true, deliverCallback, consumerTag -> { }); */

        GetResponse getResponse;
        do {
            getResponse = ch.basicGet("messageFromDB", true);
        } while (getResponse==null);

        String message = new String(getResponse.getBody(), "UTF-8");

        System.out.println(" [x] Received '" + message + "'");

        return message;
    }
    public String receiveLoginRespondFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("respondCheckLogin", false, false, false, null);
        //System.out.println(" [*] Waiting for messages from database");
        /**
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("respondCheckLogin", true, deliverCallback, consumerTag -> { });
         */

        GetResponse getResponse;
        do {
            getResponse = ch.basicGet("respondCheckLogin", true);
        } while (getResponse==null);

        String message = new String(getResponse.getBody(), "UTF-8");

        System.out.println(" [x] Received '" + message + "'");

        return message;
    }
    public String receiveVerifyNotificationFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("respondVerify", false, false, false, null);

        GetResponse getResponse;
        do {
            getResponse = ch.basicGet("respondVerify", true);
        } while (getResponse==null);

        String message = new String(getResponse.getBody(), "UTF-8");

        System.out.println(" [x] Received '" + message + "'");

        return message;
    }
    public String receiveOrderRespondFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("orderRespondFromDB", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("orderRespondFromDB", true, deliverCallback, consumerTag -> { });

        return message;
    }
    public String receiveOrderPriceFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("orderPriceFromDB", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("orderPriceFromDB", true, deliverCallback, consumerTag -> { });

        return message;
    }
    public String receiveOrderStatusFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("respondWalletPayment", false, false, false, null);
        //System.out.println(" [*] Waiting for messages from database");
        /**
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("respondWalletPayment", true, deliverCallback, consumerTag -> { });*/

        GetResponse getResponse;
        do {
            getResponse = ch.basicGet("respondWalletPayment", true);
        } while (getResponse==null);

        String message = new String(getResponse.getBody(), "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
        return message;
    }
    public String receiveTransferCodeFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("sendTransferCodeToUser", false, false, false, null);
        // System.out.println(" [*] Waiting for messages from database");
        /**
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("sendTransferCodeToUser", true, deliverCallback, consumerTag -> { });*/

        GetResponse getResponse;
        do {
            getResponse = ch.basicGet("sendTransferCodeToUser", true);
        } while (getResponse==null);

        String message = new String(getResponse.getBody(), "UTF-8");
        System.out.println(" [x] Received '" + message + "'");

        return message;
    }


    public String receiveErrorProviderCodeFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("sendErrorProvider", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("sendErrorProvider", true, deliverCallback, consumerTag -> { });

        return message;
    }
    public String receiveErrorOrderFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("sendErrorOrder", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        ch.basicConsume("sendErrorOrder", true, deliverCallback, consumerTag -> { });

        return message;
    }

    // Receive List From DB
    public String receiveListFromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("messageFromProviderToDBToUser", false, false, false, null);
        //System.out.println(" [*] Waiting for messages from database");
        /**
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received List From Database");
            System.out.println(message);
        };
        ch.basicConsume("messageFromProviderToDBToUser", true, deliverCallback, consumerTag -> { });*/

        GetResponse getResponse;
        do {
            getResponse = ch.basicGet("messageFromProviderToDBToUser", true);
        } while (getResponse==null);

        String message = new String(getResponse.getBody(), "UTF-8");
        System.out.println(" [x] Received '" + message + "'");

        return message;
    }
    public String receiveList2FromDB() throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel ch = con.createChannel();

        ch.queueDeclare("messageFromProvider2ToDBToUser", false, false, false, null);
        /**
        System.out.println(" [*] Waiting for messages from database");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //String message = new String(delivery.getBody(), "UTF-8");
            this.message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received List From Database");
            System.out.println(message);
        };
        ch.basicConsume("messageFromProvider2ToDBToUser", true, deliverCallback, consumerTag -> { });
        */

        GetResponse getResponse;
        do {
            getResponse = ch.basicGet("messageFromProvider2ToDBToUser", true);
        } while (getResponse==null);

        String message = new String(getResponse.getBody(), "UTF-8");
        System.out.println(" [x] Received '" + message + "'");

        return message;

    }
}
