package com.topup.restapi.response;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.topup.database.model.order.Order;
import com.topup.database.model.user.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service("sangPengirim")
public class controllerMessage {

    // RestAPI --> DB
    // Mengambil data dari RestAPI (via Postman) dan mengirim ke DB APP dengan JSON String
    public void registrasiUser(User user) throws IOException, TimeoutException{
        String message = new Gson().toJson(user);

        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();

        try{
            channel.queueDeclare("regisUser", false, false, false, null);
            channel.basicPublish("", "regisUser", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[x] Sending json string to RabbitMQ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void verifikasiUser(User user) throws IOException, TimeoutException{
        String message = new Gson().toJson(user);

        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();

        try{
            channel.queueDeclare("verifUser", false, false, false, null);
            channel.basicPublish("", "verifUser", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[x] Sending json string to RabbitMQ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loginUser(User user) throws IOException, TimeoutException {
        String message = new Gson().toJson(user);

        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();

        try{
            channel.queueDeclare("loginUser", false, false, false, null);
            channel.basicPublish("", "loginUser", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[x] Sending json string to RabbitMQ");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // Meminta pesan untuk request data ke provider dari RestAPI ke DB APP
    public void requestDataProvider() throws IOException, TimeoutException{

        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();

        String message = "Request All Provider1 List";

        try{
            channel.queueDeclare("askProviderList", false, false, false, null);
            channel.basicPublish(
                    "",
                    "askProviderList",
                    null,
                    message.getBytes(StandardCharsets.UTF_8)
            );
            System.out.println(" [x] Sending Request to RabbitMQ ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void requestDataProvider2() throws IOException, TimeoutException {

        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();

        String message = "Request All Provider List";

        try{
            channel.queueDeclare("askProvider2List", false, false, false, null);
            channel.basicPublish(
                    "",
                    "askProvider2List",
                    null,
                    message.getBytes(StandardCharsets.UTF_8)
            );
            System.out.println(" [x] Sending Request to RabbitMQ ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Mengambil data dari RestAPI (via Postman) dan mengirim ke DB APP
    public void buyProduct(Order order) throws IOException, TimeoutException {

        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();

        try{
            channel.queueDeclare("inputOrderFromUser", false, false, false, null);

            String inputMessage = new Gson().toJson(order);

            channel.basicPublish(
                    "",
                    "inputOrderFromUser",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );
            System.out.println(" [x] Sending Request to RabbitMQ ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
    public void buyProductCode(String inputMessage) throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();

        try{
            channel.queueDeclare("askProductCodeFromDB", false, false, false, null);
            channel.basicPublish(
                    "",
                    "askProductCodeFromDB",
                    null,
                    inputMessage.getBytes(StandardCharsets.UTF_8)
            );
            System.out.println(" [x] Sending Request to RabbitMQ ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buyProductMethods(String inputMetode) throws IOException, TimeoutException {
        ConnectionFactory conFac = new ConnectionFactory();
        conFac.setHost("localhost");
        Connection con = conFac.newConnection();
        Channel channel = con.createChannel();


    } */

}
