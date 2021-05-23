package com.topup.database.response;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.topup.database.model.message.Message;
import com.topup.provider.model.Provider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class dbmessage {
    private ConnectionFactory connectionFactory;

    // DB --> RabbitMQ --> RestAPI
    public void sendMessagetoRestController(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("messageFromDB", false, false, false, null);
            channel.basicPublish(
                    "",
                    "messageFromDB",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendAdmitRequestErrorToUser(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("requestErrorFromDB", false, false, false, null);
            channel.basicPublish(
                    "",
                    "requestErrorFromDB",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendOrderRespondtoRestController(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("OrderRespondFromDB", false, false, false, null);
            channel.basicPublish(
                    "",
                    "OrderRespondFromDB",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent Message to Rest Controller via RabbitMQ");

        } catch(Exception e){
            e.printStackTrace();
        }

    }
    public void sendOrderPricetoRestController(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("orderPriceFromDB", false, false, false, null);
            channel.basicPublish(
                    "",
                    "orderPriceFromDB",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent Message to Rest Controller via RabbitMQ");

        } catch(Exception e){
            e.printStackTrace();
        }

    }
    public void sendLoginRespond(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("respondCheckLogin", false, false, false, null);

            Message message = new Message(inputMessage);

            String input = new Gson().toJson(message);

            channel.basicPublish(
                    "",
                    "respondCheckLogin",
                    null,
                    input.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendVerifyRespond(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {

            channel.queueDeclare("respondVerify", false, false, false, null);
            Message message = new Message(inputMessage);
            String input = new Gson().toJson(message);
            channel.basicPublish(
                    "",
                    "respondVerify",
                    null,
                    input.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendMethodErrorRespond(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("respondWrongMethod", false, false, false, null);
            channel.basicPublish(
                    "",
                    "respondWrongMethod",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendWalletPaymentDone(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("respondWalletPayment", false, false, false, null);
            channel.basicPublish(
                    "",
                    "respondWalletPayment",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendErrorProviderCode(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("sendErrorProvider", false, false, false, null);
            channel.basicPublish(
                    "",
                    "sendErrorProvider",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendErrorOrder(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("sendErrorOrder", false, false, false, null);
            channel.basicPublish(
                    "",
                    "sendErrorOrder",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[x] Sent " + inputMessage);

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    // RestAPI --> RabbitMQ --> DB --> Provider
    public void sendMessagetoProvider(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("messageFromDBtoProvider", false, false, false, null);
            channel.basicPublish(
                    "",
                    "messageFromDBtoProvider",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent request to Provider" );

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendMessagetoProvider2(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("messageFromDBtoProvider2", false, false, false, null);
            channel.basicPublish(
                    "",
                    "messageFromDBtoProvider2",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent request to Provider" );

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendAdmitRequestToProvider(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("AdmitRequestFromDBtoProvider", false, false, false, null);
            channel.basicPublish(
                    "",
                    "AdmitRequestFromDBtoProvider",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent request to Provider" );

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendAdmitRequestToProvider2(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("AdmitRequestFromDBtoProvider2", false, false, false, null);
            channel.basicPublish(
                    "",
                    "AdmitRequestFromDBtoProvider2",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent request to Provider2" );

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendStatusRespondToProvider(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("sendToProviderNotif", false, false, false, null);
            channel.basicPublish(
                    "",
                    "sendToProviderNotif",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent request to Provider2" );

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendStatusRespondToProvider2(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("sendToProvider2Notif", false, false, false, null);
            channel.basicPublish(
                    "",
                    "sendToProvider2Notif",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent request to Provider2" );

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    // Provider --> RabbitMQ --> DB --> RabbitMQ --> RestAPI
    public void ListProductToRestController(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("messageFromProviderToDBToUser", false, false, false, null);
            channel.basicPublish(
                    "",
                    "messageFromProviderToDBToUser",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent message from database to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ListProduct2ToRestController(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("messageFromProvider2ToDBToUser", false, false, false, null);
            channel.basicPublish(
                    "",
                    "messageFromProvider2ToDBToUser",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent message from database to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendProductMethodToBank(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("requestMethodToBank", false, false, false, null);

            channel.basicPublish("", "requestMethodToBank", null, inputMessage.getBytes(StandardCharsets.UTF_8));

            System.out.println("[x] Sent provider list to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Bank --> RabbitMQ --> DB --> RabbitMQ --> RestAPI
    public void sendTransferCodeToRestController(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("sendTransferCodeToUser", false, false, false, null);

            Message message = new Message("Silahkan Transfer melalui Virtual Account dengan Kode : " + inputMessage);

            String pesan = new Gson().toJson(message);

            channel.basicPublish(
                    "",
                    "sendTransferCodeToUser",
                    null,
                    pesan.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(" [x] Sent message from database to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
