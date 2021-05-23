package com.topup.bank.response;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class sendBank {
    private ConnectionFactory connectionFactory;
    private Connection con;
    private Channel ch;

    public void connectRabbit() throws IOException, TimeoutException {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        con = connectionFactory.newConnection();
    }

    public void sendTransferCodeToDB(String inputMessage) throws IOException, TimeoutException {
        connectRabbit();

        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {

            channel.queueDeclare("SendTransfercodeToDB", false, false, false, null);

            channel.basicPublish("", "SendTransfercodeToDB", null, inputMessage.getBytes(StandardCharsets.UTF_8));

            System.out.println("[x] Sent provider list to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
