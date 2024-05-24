package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    @Value("${server.port}")
    private int serverPort;
    private Logger logger = LoggerFactory.getLogger(InfoService.class);
    public String getPort(){
        logger.info("Info about port is returned.");
        return "Current port is " + serverPort;
    }
}
