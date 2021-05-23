package com.topup.bank;

import com.topup.bank.response.receiveBank;
import com.topup.provider.response.receive;
import com.topup.restapi.SpringBootMainApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class BankMain {
    private static receiveBank message = new receiveBank();

    public static void main(String[] args){
        SpringApplication application = new SpringApplication(BankMain.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", "8887"));
        application.run(args);

        //SpringApplication.run(BankMain.class, args);

        System.out.println("[x] Waiting for message");
        try{
            message.messagePhoneFromDB();  // Menerima pesan nomor telepon untuk membuat kode transfer virtual account
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
