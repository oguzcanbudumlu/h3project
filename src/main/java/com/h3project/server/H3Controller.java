package com.h3project.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class H3Controller {
    @Autowired
    H3Service h3Service;
}
