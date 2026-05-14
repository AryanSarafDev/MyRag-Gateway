package com.myrag.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myrag.gateway.Service.QueryService;
import com.myrag.gateway.models.QueryRequest;


@RestController
@RequestMapping("/api/v1") 
public class GatewayController {
private final QueryService queryService;

public GatewayController(QueryService queryService) {
    this.queryService = queryService;
}

@GetMapping("/health")
public ResponseEntity<String> health(){
    return ResponseEntity.ok("Gateway is running!");
}

@PostMapping("/query")
public ResponseEntity<String> handleQuery(@RequestBody QueryRequest request) {
    String response = queryService.process(request);
    return ResponseEntity.ok(response);
}


}