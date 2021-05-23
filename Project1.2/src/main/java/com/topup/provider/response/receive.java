package com.topup.provider.response;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.topup.provider.model.Provider;
import com.topup.database.response.dbmessage;
import com.topup.provider.service.ProductService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class receive {
    private ConnectionFactory connectionFactory;
    private Connection con;
    private Channel ch;
    private ProductService productService = new ProductService();
    private send messageToDB = new send();

    public void connectRabbit() throws IOException, TimeoutException {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        con = connectionFactory.newConnection();
    }
    public void messageFromDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("messageFromDBtoProvider", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received " + message);
                try{
                    productService.findAllProduct();
                } catch (TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("messageFromDBtoProvider", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requestProductFromDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("AdmitRequestFromDBtoProvider", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received message from Database via RabbitMQ");
                System.out.println(" [x] Input data to Provider Database");

                try{
                    //productService.countProductDB(message);
                    productService.findProductPriceToDB(message);
                } catch (TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("AdmitRequestFromDBtoProvider", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void statusPaymentFromDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("sendToProviderNotif", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received " + message);
                try{
                    //productService.findAllProduct();
                    productService.countProductDB(message);
                } catch (TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("sendToProviderNotif", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
