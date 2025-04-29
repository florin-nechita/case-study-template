package com.rohlikgroup.casestudy.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReleaseUnpaidOrdersScheduler {


    @Scheduled(fixedRate = 5000)
    public void releaseUnpaidOrders() {
        // Logic to release unpaid orders
        // This method should be scheduled to run at a specific interval
        // For example, every hour or every day
    }

}
