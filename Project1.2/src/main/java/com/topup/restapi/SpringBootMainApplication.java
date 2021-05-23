package com.topup.restapi;

import com.topup.restapi.response.controllerReceive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.util.Collections;

@SpringBootApplication
public class SpringBootMainApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringBootMainApplication.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", "8888"));
        application.run(args);

        //SpringApplication.run(SpringBootMainApplication.class, args);

        // Memmanggil method untuk menerima message dari database
        controllerReceive terima = new controllerReceive();
        try {
            //terima.receiveMessageFromDB();
            //terima.receiveListFromDB();
            //terima.receiveOrderRespondFromDB();
            terima.receiveErrorProviderCodeFromDB();
            terima.receiveErrorOrderFromDB();
            //terima.receiveVerifyNotificationFromDB();
            //terima.receiveOrderPriceFromDB();
            //terima.receiveOrderStatusFromDB();
            //terima.receiveTransferCodeFromDB();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
