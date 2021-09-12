package com.example.event;

import org.springframework.context.ApplicationListener;

public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

    @Override
    public void onApplicationEvent(CustomSpringEvent customSpringEvent) {
        System.out.println("Received spring custom event - " + customSpringEvent.getMessage());
    }
}
