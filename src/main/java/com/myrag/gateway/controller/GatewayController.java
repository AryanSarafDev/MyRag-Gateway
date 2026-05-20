package com.myrag.gateway.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myrag.gateway.Service.CacheService;
import com.myrag.gateway.Service.QueryService;
import com.myrag.gateway.Service.RateLimiterService;
import com.myrag.gateway.models.QueryRequest;


@RestController
@RequestMapping("/api/v1") 
public class GatewayController {
private final QueryService queryService;
private final RateLimiterService rateLimiterService;
private final CacheService cacheService;

public GatewayController(QueryService queryService, RateLimiterService rateLimiterService, CacheService cacheService) {
    this.queryService = queryService;
    this.rateLimiterService = rateLimiterService;
    this.cacheService = cacheService;
}

@GetMapping("/health")
public ResponseEntity<String> health(){
    return ResponseEntity.ok("Gateway is running!");
}

@PostMapping("/query")
public ResponseEntity<String> handleQuery(@RequestBody QueryRequest request) {
    if(!rateLimiterService.isAllowed(request.getUserId())){
        return ResponseEntity.status(429).body("Rate limit exceeded . Max" + 20 + "response per minute.");
    }

    if(request.getTemperature()==0){
        Optional<String> cached = cacheService.getCachedResponse(request.getQuery());
        if(cached.isPresent()){
            return ResponseEntity.ok("[FROM CACHE]" + cached.get());
        }
    }

    String response = queryService.process(request);
    if(request.getTemperature()==0){
        cacheService.cacheResponse(request.getQuery(), response);
    }

    return ResponseEntity.ok(response);
}


}