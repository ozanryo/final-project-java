package com.topup.provider2.response;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.topup.provider2.model.Provider2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class send {

    private ConnectionFactory connectionFactory;
    private Connection con;
    private Channel ch;

    public void connectRabbit() throws IOException, TimeoutException {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        con = connectionFactory.newConnection();
    }

    public void sendProvider2ListtoDB(List<Provider2> provider){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        /**
        StringBuilder inputMessage = new StringBuilder();
        for(int i=0; i<provider.size(); i++){
            int code = provider.get(i).getCode();
            Double pulsa = provider.get(i).getPulsa();
            Double harga = provider.get(i).getHarga();

            inputMessage.append(
                    "\n Kode Pulsa : " + code +
                    "\n Nominal Pulsa : " + pulsa +
                    "\n Nominal Harga : " + harga + "\n"
            );
        }*/

        String inputMessage = new Gson().toJson(provider);

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("listFromProvider2", false, false, false, null);

            channel.basicPublish("", "ListFromProvider2", null, inputMessage.toString().getBytes(StandardCharsets.UTF_8));

            System.out.println("[x] Sent provider2 list to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendRequestCodePrice2ToDB(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {

            channel.queueDeclare("SendCodePriceFromProvider2ToDB", false, false, false, null);

            channel.basicPublish("", "SendCodePriceFromProvider2ToDB", null, inputMessage.getBytes(StandardCharsets.UTF_8));

            System.out.println("[x] Sent provider2 list to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
