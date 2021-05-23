package com.topup.provider.response;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.topup.provider.model.Provider;
import com.topup.database.response.dbmessage;
import com.topup.database.service.userService;

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

    public void sendProviderListtoDB(List<Provider> provider){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        /**
         * // Mengirim dengan Format String dan Text
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

        String inputMessage = new Gson().toJson(provider); // Mengirim dengan Format JSON

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {
            channel.queueDeclare("listFromProvider", false, false, false, null);

            channel.basicPublish("", "ListFromProvider", null, inputMessage.toString().getBytes(StandardCharsets.UTF_8));

            System.out.println("[x] Sent provider list to user ");


        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void sendRequestCodePriceToDB(String inputMessage){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
                Connection con = connectionFactory.newConnection();
                Channel channel = con.createChannel();
        ) {

            channel.queueDeclare("SendCodePriceFromProviderToDB", false, false, false, null);

            channel.basicPublish("", "SendCodePriceFromProviderToDB", null, inputMessage.getBytes(StandardCharsets.UTF_8));

            System.out.println("[x] Sent provider list to user ");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
