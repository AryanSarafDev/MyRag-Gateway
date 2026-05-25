package com.myrag.gateway.models;

import lombok.Data;

@Data
public class AiQueryResponse {
    private String answer;
    private String source;
}
