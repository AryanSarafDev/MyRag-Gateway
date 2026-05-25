package com.myrag.gateway.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.myrag.gateway.models.AiQueryRequest;
import com.myrag.gateway.models.AiQueryResponse;

@Service
public class AiService {

        private final WebClient aiServiceWebClient;

        public AiService(WebClient aiServiceWebClient) {
            this.aiServiceWebClient = aiServiceWebClient;
        }

        public AiQueryResponse query(String query, float temperature){
            AiQueryRequest request = new AiQueryRequest(query, temperature);
            
            return aiServiceWebClient
            .post()
            .uri("/query")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(AiQueryResponse.class)
            .block();
        }


}
