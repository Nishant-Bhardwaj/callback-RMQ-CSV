package com.utility.callback.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service Interface for CSV processing
 *
 * @author Nishant Bhardwaj
 */
@Service
public interface CSVService {

    void processCSV(MultipartFile csvFile) throws Exception;
}
