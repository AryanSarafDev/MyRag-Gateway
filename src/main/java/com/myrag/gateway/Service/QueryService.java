package com.myrag.gateway.Service;

import org.springframework.stereotype.Service;

import com.myrag.gateway.models.AiQueryResponse;
import com.myrag.gateway.models.QueryRequest;

@Service
public class QueryService {

    private AiService aiService;

    public QueryService(AiService aiService) {
        this.aiService = aiService;
    }

    public String process(QueryRequest request) {
        AiQueryResponse response = aiService.query(request.getQuery(), request.getTemperature());

        return String.format("Answer: %s | Source: %s",
                response.getAnswer(),
                response.getSource());
    }

}
