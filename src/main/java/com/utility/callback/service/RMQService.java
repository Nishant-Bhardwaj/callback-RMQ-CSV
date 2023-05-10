package com.utility.callback.service;

import org.springframework.stereotype.Service;

/**
 * Service Interface for Service communication
 *
 * @author Nishant Bhardwaj
 */
@Service
public interface RMQService {

    String publishMessage(String message);
}
