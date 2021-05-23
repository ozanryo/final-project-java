package com.topup.provider2;


import com.topup.provider.ProviderMain;
import com.topup.provider2.response.receive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class Provider2Main {

    private static receive message = new receive();

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Provider2Main.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", "8885"));
        application.run(args);

        //SpringApplication.run(ProviderMain.class, args);

        System.out.println("[x] Waiting for message");
        try {
            message.messageFromDB();
            message.requestProduct2FromDB();
            message.statusPaymentFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
