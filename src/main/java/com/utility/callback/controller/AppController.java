package com.utility.callback.controller;

import com.utility.callback.service.CSVService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

/**
 * Controller having endpoint to process CSV request
 *
 * @author Nishant Bhardwaj
 */
@RestController
@RequestMapping(path = "/v1")
public class AppController {

    private Logger logger = LogManager.getLogger(AppController.class);

    @Autowired
    CSVService csvService;

    @PostMapping(path = "/callbackCSV")
    public ResponseEntity<Object> uploadCSV(@RequestParam("csvFile") MultipartFile csvFile) {
        logger.info("UploadCSV :: START");

        // Async service call:
        CompletableFuture.runAsync(() -> {
            try {
                csvService.processCSV(csvFile);
            } catch (Exception e) {
                logger.error("Exception :" + e.getMessage());
            }
        });

        return new ResponseEntity<>("File Received, in process",HttpStatus.OK);

    }


}
