package com.teashop.Membership.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teashop.Membership.dto.response.SubscriptionResponseDTO;
import com.teashop.Membership.service.SubscriptionService;
import com.teashop.Membership.util.ResponseStructure;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Tmembership")
public class SubscriptionController {

    private final SubscriptionService service;

 

    @PutMapping("/Subscription/use-tea")
    public ResponseEntity<ResponseStructure<SubscriptionResponseDTO>> useTea(
            @RequestParam String phone) {
        return service.useTea(phone);
    }

    @GetMapping("/Subscription/active")
    public ResponseEntity<ResponseStructure<SubscriptionResponseDTO>> getActiveSubscription(
            @RequestParam String phone) {
        return service.getActiveSubscription(phone);
    }

    @GetMapping("/Subscription/history")
    public ResponseEntity<ResponseStructure<List<SubscriptionResponseDTO>>> getHistory(
            @RequestParam String phone) {
        return service.getHistory(phone);
    }
}