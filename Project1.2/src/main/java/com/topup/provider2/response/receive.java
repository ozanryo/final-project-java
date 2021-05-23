package com.topup.provider2.response;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.topup.provider2.service.Product2Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class receive {

    private ConnectionFactory connectionFactory;
    private Connection con;
    private Channel ch;
    private Product2Service productService = new Product2Service();

    public void connectRabbit() throws IOException, TimeoutException {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        con = connectionFactory.newConnection();
    }
    public void messageFromDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("messageFromDBtoProvider2", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received " + message);
                try{
                    productService.findAllProduct2();
                } catch (TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("messageFromDBtoProvider2", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requestProduct2FromDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("AdmitRequestFromDBtoProvider2", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received message from Database via RabbitMQ");
                System.out.println(" [x] Input data to Provider Database");

                try{
                    //productService.countProduct2DB(message);
                    productService.findProduct2PriceToDB(message);
                } catch (TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("AdmitRequestFromDBtoProvider2", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void statusPaymentFromDB(){
        try{
            connectRabbit();
            ch = con.createChannel();
            ch.queueDeclare("sendToProvider2Notif", false, false,false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(
                        delivery.getBody(),
                        StandardCharsets.UTF_8
                );
                System.out.println(" [x] Received " + message);
                try{
                    //productService.findAllProduct();
                    productService.countProduct2DB(message);
                } catch (TimeoutException e){
                    e.printStackTrace();
                }
            };
            ch.basicConsume("sendToProvider2Notif", true, deliverCallback, consumerTag -> { });
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
