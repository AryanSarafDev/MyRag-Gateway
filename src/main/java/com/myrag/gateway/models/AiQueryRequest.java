package com.myrag.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiQueryRequest {
private String query;
private float temperature;
}
