package com.topup.restapi.controller;

import com.topup.database.model.order.Order;
import com.topup.database.model.user.User;
import com.topup.restapi.response.controllerMessage;
import com.topup.restapi.response.controllerReceive;
import com.topup.restapi.util.utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/topup-pulsa")
public class RestApiController {
    private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
    private controllerReceive conReceive = new controllerReceive();
    private utility Util = new utility();

    @Autowired
    controllerMessage sangPengirim;

    //------------------------------------------------------------------------------------------------------
    //                                         REGISTRASI USER BARU
    //------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/registrasi", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) throws SQLException, IOException, TimeoutException {
        logger.info("Sending information of new user : {}", user);

        //String info;
        /**
        if (
                (Util.checkUsernameRegex(user) == true) && (Util.checkPasswordRegex(user)==true)
        ) {
            sangPengirim.registrasiUser(user);            // memanggil fungsi response
            info = conReceive.receiveMessageFromDB();
            //return (new ResponseEntity<>(conReceive.receiveMessageFromDB(), HttpStatus.CREATED));

        } else {
            info = "Regex Salah, silahkan input username dan password yang lain";
            //return (new ResponseEntity<>(error, HttpStatus.CREATED));
        }*/
        sangPengirim.registrasiUser(user);            // memanggil fungsi response
        String info = conReceive.receiveMessageFromDB();

        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------
    //                                         VERIFIKASI USER BARU
    //------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/verifikasi", method = RequestMethod.POST)
    public ResponseEntity<?> verifyUser(@RequestBody User user) throws SQLException, IOException, TimeoutException {
        logger.info("Sending information of new user : {}", user);

        sangPengirim.verifikasiUser(user);

        String info = conReceive.receiveVerifyNotificationFromDB();

        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------
    //                                            LOGIN USERNAME
    //------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody User user) throws SQLException, IOException, TimeoutException {
        logger.info("Sending information for login : {}", user);

        try{
           sangPengirim.loginUser(user);// memanggil fungsi response
        } catch (Exception e){
           e.printStackTrace();
        }
        return new ResponseEntity<>(conReceive.receiveLoginRespondFromDB(), HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------
    //                                       MELIHAT PULSA PROVIDER 1
    //------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/lihat-daftar/provider1", method = RequestMethod.GET)
    public ResponseEntity<?> showProduct() throws SQLException, IOException, TimeoutException {
        logger.info("Sending information to show the product from all provider");
        try{
            sangPengirim.requestDataProvider();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(conReceive.receiveListFromDB(), HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------
    //                                       MELIHAT PULSA PROVIDER 2
    //------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/lihat-daftar/provider2", method = RequestMethod.GET)
    public ResponseEntity<?> showProduct2() throws SQLException, IOException, TimeoutException {
        logger.info("Sending information to show the product from all provider");
        try{
            sangPengirim.requestDataProvider2();
        } catch (Exception e){
            e.printStackTrace();
        }
        String info = conReceive.receiveList2FromDB();
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------
    //                                         MEMBELI PRODUK PULSA
    //------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/order-pulsa", method = RequestMethod.POST)
    public ResponseEntity<?> buyProduct(@RequestBody Order order) throws SQLException, IOException, TimeoutException {
        logger.info("Sending information to database for buying produk");
        sangPengirim.buyProduct(order);
        String info = null;

        if (order.getMetode().equals("wallet")) {

            conReceive.receiveOrderRespondFromDB();
            conReceive.receiveOrderPriceFromDB();
            info = conReceive.receiveOrderStatusFromDB();
            //return new ResponseEntity<>(conReceive.receiveOrderPriceFromDB(), HttpStatus.CREATED);

        } else if (order.getMetode().equals("virtual account")) {

            conReceive.receiveOrderRespondFromDB();
            conReceive.receiveOrderPriceFromDB();

            //String message = conReceive.receiveErrorProviderCodeFromDB();
            /**
            if (message.equals("provider tersedia")){
                //info = conReceive.receiveOrderRespondFromDB();
                info = conReceive.receiveTransferCodeFromDB();
            } else {
                info = conReceive.receiveErrorProviderCodeFromDB();
            }*/

            info = conReceive.receiveTransferCodeFromDB();

            //return new ResponseEntity<>(info, HttpStatus.CREATED);

        } else {
            info = "Metode Produk tidak tersedia";
            //return new ResponseEntity<>(info, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

}
