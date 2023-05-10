package com.utility.callback.service.impl;

import com.utility.callback.service.CSVService;
import com.utility.callback.service.RMQService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.internal.commons.io.input.BOMInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Service Implementation for CSV processing
 *
 * @author Nishant Bhardwaj
 */
@Service
public class CSVServiceImpl implements CSVService {

    private Logger logger = LogManager.getLogger(CSVServiceImpl.class);

    @Autowired
    RMQService rmqService;


    /**
     * Service will take InputStream of csv File and process request
     * @param csvFile
     * @author Nishant Bhardwaj
     */
    @Override
    public void processCSV(MultipartFile csvFile) throws Exception {

        logger.info("Process CSV:: START, csvFile name: "+ csvFile.getOriginalFilename());

        InputStream inputStream = csvFile.getInputStream();

        Reader fileReader = new InputStreamReader(
                new BOMInputStream(inputStream), "UTF-8"
        );

        CSVParser csvParser = new CSVParser(
                fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
        );

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for(CSVRecord csvRecord : csvRecords){

            String message = csvRecord.get("request_json");

            // Send message to RMQ:
            rmqService.publishMessage(message);

        }
        logger.info("Process CSV:: END, csvFile name: "+ csvFile.getOriginalFilename());

    }

}
