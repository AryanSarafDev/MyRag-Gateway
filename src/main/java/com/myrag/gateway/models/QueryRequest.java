package com.myrag.gateway.models;

import lombok.Data;

@Data
public class QueryRequest {

    private String userId;
    private String query;
    private float temperature;
}
