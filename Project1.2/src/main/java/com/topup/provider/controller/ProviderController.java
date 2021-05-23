package com.topup.provider.controller;

import com.topup.database.model.user.User;
import com.topup.provider.model.Provider;
import com.topup.provider.response.receive;
import com.topup.provider.response.send;
import com.topup.provider.service.ProductService;
import com.topup.restapi.response.controllerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/provider")
public class ProviderController {
    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    //------------------------------------------------------------------------------------------------------
    //                                         MELAKUKAN UPDATE PRODUK
    //------------------------------------------------------------------------------------------------------


}
