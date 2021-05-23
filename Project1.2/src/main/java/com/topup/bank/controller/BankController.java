package com.topup.bank.controller;

import com.topup.restapi.response.controllerMessage;
import com.topup.restapi.response.controllerReceive;
import com.topup.restapi.util.utility;
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
@RequestMapping("/bank")
public class BankController {
}
