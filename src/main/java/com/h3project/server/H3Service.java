package com.h3project.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class H3Service {

    @Autowired
    H3Dao h3Dao;


}
