package com.topup.provider;

import com.topup.provider.response.receive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ProviderMain {

    private static receive message = new receive();

    public static void main(String[] args){
        SpringApplication application = new SpringApplication(ProviderMain.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", "8886"));
        application.run(args);

        //SpringApplication.run(ProviderMain.class, args);
        System.out.println("[x] Waiting for message");
        try{
            message.messageFromDB();
            message.requestProductFromDB();
            message.statusPaymentFromDB();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
