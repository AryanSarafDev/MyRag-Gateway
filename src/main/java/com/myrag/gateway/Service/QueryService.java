package com.myrag.gateway.Service;

import org.springframework.stereotype.Service;

import com.myrag.gateway.models.QueryRequest;

@Service
public class QueryService {

        public String process(QueryRequest request) {
             return String.format(
            "Received query '%s' from user '%s' with temperature %.1f",
            request.getQuery(),
            request.getUserId(),
            request.getTemperature()
        );
        }   
}
